package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.datasource.cache.credentials.CredentialsCacheDataSource
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.datasource.cloud.CredentialsCloudDataSource
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.model.DataGetCredentialById
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject

class StoredCredentialsRepositoryImpl @Inject constructor(
    private val apiService: CredentialsApiService,
    private val apiCallHelper: ApiCallHelper,
    private val credentialsCloudDataSource: CredentialsCloudDataSource,
    private val credentialsCacheDataSource: CredentialsCacheDataSource,
    private val mapper: SignedCredentialToDomainMapper,
) : StoredCredentialsRepository {

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

    override suspend fun getCredentialById(
        getCredentialByIdReqModel: GetCredentialByIdReqModel, authState: AuthStateModel
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


    override suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel, authState: AuthStateModel): DeleteVcResModel {

        val res = apiCallHelper.executeCall {
            apiService.deleteVc(
                credentialId = deleteVcReqModel.credentialId, accessToken = authState.accessToken ?: ""
            )
        }

        return when (res) {
            is ApiCallWrapper.Success -> DeleteVcResModel.Success
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    // TODO review and fix 401
                    401 -> DeleteVcResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> DeleteVcResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> DeleteVcResModel.Fail(res.errorType)
        }
    }


    override suspend fun shareCredential(shareVcReq: ShareCredentialReqModel, authState: AuthStateModel): ShareCredentialResModel {

        val res = apiCallHelper.executeCall {
            apiService.shareVC(
                credentialId = shareVcReq.credentialId, accessToken = authState.accessToken ?: ""
            )
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                // Cut to only base64 value
                val base64 = res.data.qrCode.replace("data:image/png;base64,", "")
                ShareCredentialResModel.Success(base64QrCode = base64)
            }
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    401 -> ShareCredentialResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> ShareCredentialResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> ShareCredentialResModel.Fail(res.errorType)
        }
    }

    override suspend fun checkIfHaveCredentialInCache(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel {
        val vcsCache = credentialsCacheDataSource.getCredentialsFromCache() as DataCredentialsList.Success
        val domainList = vcsCache.signedCredentials.map {
            mapper.map(it)
        }

        val filtered = domainList.filter {
            if (it.expirationDate == null) {
                it.vcSchema == checkIfHaveVcReqModel.vcContextUrl
            } else {
                // Return with expired
                if (checkIfHaveVcReqModel.includeExpired) {
                    it.vcSchema == checkIfHaveVcReqModel.vcContextUrl
                } else {
                    it.expirationDate!! >= System.currentTimeMillis() && it.vcSchema == checkIfHaveVcReqModel.vcContextUrl
                }
            }
        }

        Timber.debug("cecking incl expired ${checkIfHaveVcReqModel.includeExpired} result ${filtered.isNotEmpty()}")

        return CheckIfHaveVcResModel(haveCredential = filtered.isNotEmpty())
    }

}