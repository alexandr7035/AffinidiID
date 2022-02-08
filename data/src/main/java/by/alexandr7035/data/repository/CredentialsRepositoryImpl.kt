package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelper
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.datasource.cloud.CredentialsCloudDataSource
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.model.DataGetCredentialById
import by.alexandr7035.data.model.network.credentials.share_vc.ShareVcRes
import by.alexandr7035.data.model.network.credentials.verify_vcs.VerifyVCsReq
import by.alexandr7035.data.model.network.credentials.verify_vcs.VerifyVCsRes
import com.google.gson.Gson
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CredentialsRepositoryImpl @Inject constructor(
    private val apiService: CredentialsApiService,
    private val vcIssuanceHelper: VCIssuanceHelper,
    private val credentialsCloudDataSource: CredentialsCloudDataSource,
    private val credentialsCacheDataSource: CredentialsCacheDataSource,
    private val mapper: SignedCredentialToDomainMapper,
    // TODO move out
    private val gson: Gson
) : CredentialsRepository {

    override suspend fun getAllCredentials(authState: AuthStateModel): Flow<CredentialsListResModel> {

        return flow {
            // Cache is always success even if empty list
            val cacheCredentials = credentialsCacheDataSource.getCredentialsFromCache() as DataCredentialsList.Success
            val domainCache = cacheCredentials.signedCredentials.map {
                mapper.map(it)
            }

            // Always try firstly return cache
            // If empty show loading UI
            if (cacheCredentials.signedCredentials.isEmpty()) {
                emit(CredentialsListResModel.Loading)
            } else {
                emit(CredentialsListResModel.Success(credentials = domainCache))
            }

            // Load from cloud
            val cloudCredentials = credentialsCloudDataSource.getCredentialsFromCloud(authState)

            // When cloud update fails return cache if not empty
            // Otherwise return error
            if (cloudCredentials is DataCredentialsList.Fail) {

                if (domainCache.isNotEmpty()) {
                    emit(CredentialsListResModel.Success(credentials = domainCache))
                } else {
                    emit(CredentialsListResModel.Fail(errorType = cloudCredentials.errorType))
                }
            }
            // When success cloud update also save to cache
            else if (cloudCredentials is DataCredentialsList.Success) {
                credentialsCacheDataSource.clearCredentialsCache()
                credentialsCacheDataSource.saveCredentialsToCache(cloudCredentials)

                val domainCloud = cloudCredentials.signedCredentials.map {
                    mapper.map(it)
                }

                emit(CredentialsListResModel.Success(credentials = domainCloud))
            }
        }.flowOn(Dispatchers.IO)
    }


    // 1) Build unsigned VC
    // 2) Sign the VC
    // 3) Store credential in the cloud wallet
    // TODO give the user choice where to store in the future
    override suspend fun issueCredential(
        issueCredentialReqModel: IssueCredentialReqModel,
        authState: AuthStateModel
    ): IssueCredentialResModel {
        try {
            val unsignedVc = vcIssuanceHelper.buildUnsignedVC(issueCredentialReqModel)
            val signedVc = vcIssuanceHelper.signCredential(unsignedVc, authState)
            // Store only 1 VC, so just get last ID from response
            val storedVCsID = vcIssuanceHelper.storeCredentials(listOf(signedVc), authState).last()

            return IssueCredentialResModel.Success

        } catch (appError: AppError) {
            return IssueCredentialResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return IssueCredentialResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }


    override suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel, authState: AuthStateModel): DeleteVcResModel {
        try {
            val res = apiService.deleteVc(
                credentialId = deleteVcReqModel.credentialId,
                accessToken = authState.accessToken ?: ""
            )

            return if (res.isSuccessful) {
                DeleteVcResModel.Success
            } else {
                when (res.code()) {
                    // TODO review and fix 401
                    401 -> DeleteVcResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> DeleteVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }

        } catch (appError: AppError) {
            return DeleteVcResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return DeleteVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun getCredentialById(
        getCredentialByIdReqModel: GetCredentialByIdReqModel,
        authState: AuthStateModel
    ): Flow<GetCredentialByIdResModel> {
        return flow {
            // Show loading first
            emit(GetCredentialByIdResModel.Loading)

            // Try to get VC from cache firstly
            // Presume that always success (as credentials list is always updated before)
            val cacheCredential =
                credentialsCacheDataSource.getCredentialByIdFromCache(getCredentialByIdReqModel.credentialId) as DataGetCredentialById.Success
            val domainCachedVC = mapper.map(cacheCredential.credential)
            emit(GetCredentialByIdResModel.Success(credential = domainCachedVC))
        }
    }

    override suspend fun verifyCredential(verifyVcReqModel: VerifyVcReqModel): VerifyVcResModel {
        try {
            // Convert to json
            val json = gson.fromJson(verifyVcReqModel.rawVc, JsonObject::class.java)

            val res = apiService.verifyVCs(
                // Verify single VC
                VerifyVCsReq(credentials = listOf(json))
            )

            return if (res.isSuccessful) {
                val data = res.body() as VerifyVCsRes
                VerifyVcResModel.Success(isValid = data.isValid)
            } else {
                // This request returns 200 always but handle error just in case
                VerifyVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
            }

        } catch (appError: AppError) {
            return VerifyVcResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return VerifyVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun shareCredential(shareVcReq: ShareCredentialReqModel, authState: AuthStateModel): ShareCredentialResModel {
        try {
            val res = apiService.shareVC(credentialId = shareVcReq.credentialId, accessToken = authState.accessToken ?: "")

            return if (res.isSuccessful) {
                val data = res.body() as ShareVcRes

                // Cut to only base64 value
                val base64 = data.qrCode.replace("data:image/png;base64,", "")

                ShareCredentialResModel.Success(base64QrCode = base64)
            } else {
                when (res.code()) {
                    401 -> ShareCredentialResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> ShareCredentialResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        } catch (appError: AppError) {
            return ShareCredentialResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return ShareCredentialResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun checkIfHaveCredentialInCache(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel {
        val haveCredential = credentialsCacheDataSource.checkIfHaveCredentialInCache(
            credentialContextUrl = checkIfHaveVcReqModel.vcContextUrl
        )

        return CheckIfHaveVcResModel(haveCredential = haveCredential)
    }

}