package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.helpers.vc_issuance.VCIssuanceHelper
import by.alexandr7035.data.local_storage.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.network.CredentialsCloudDataSource
import by.alexandr7035.data.network.api.CredentialsApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class CredentialsRepositoryImpl @Inject constructor(
    private val apiService: CredentialsApiService,
    private val vcIssuanceHelper: VCIssuanceHelper,
    private val credentialsCloudDataSource: CredentialsCloudDataSource,
    private val credentialsCacheDataSource: CredentialsCacheDataSource
) : CredentialsRepository {

    override suspend fun getAllCredentials(authState: AuthStateModel): Flow<CredentialsListResModel> {

        return flow {
            // Cache is always success even if empty list
            val cacheCredentials = credentialsCacheDataSource.getCredentialsFromCache() as CredentialsListResModel.Success

            // Always try firstly return cache
            // If empty show loading UI
            if (cacheCredentials.credentials.isEmpty()) {
                emit(CredentialsListResModel.Loading())
            }
            else {
                emit(cacheCredentials)
            }

            // Load from cloud
            val cloudCredentials = credentialsCloudDataSource.getCredentialsFromCloud(authState)

            // When cloud update fails return cache if not empty
            // Otherwise return error
            if (cloudCredentials is CredentialsListResModel.Fail) {
                if (cacheCredentials.credentials.isNotEmpty()) {
                    emit(cacheCredentials)
                }
                else {
                    emit(cloudCredentials)
                }
            }
            // When success cloud update also save to cache
            else {
                credentialsCacheDataSource.clearCredentialsCache()
                credentialsCacheDataSource.saveCredentialsToCache(cloudCredentials)
                emit(cloudCredentials)
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

            return IssueCredentialResModel.Success()

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
                DeleteVcResModel.Success()
            } else {
                when (res.code()) {
                    // TODO review and fix 401
                    401 -> {
                        DeleteVcResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    }
                    else -> {
                        DeleteVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
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

}