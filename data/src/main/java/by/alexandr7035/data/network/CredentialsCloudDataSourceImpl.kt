package by.alexandr7035.data.network

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential
import by.alexandr7035.data.network.api.CredentialsApiService
import javax.inject.Inject

class CredentialsCloudDataSourceImpl @Inject constructor(private val apiService: CredentialsApiService, private val credentialMapper: SignedCredentialToDomainMapper): CredentialsCloudDataSource {
    override suspend fun getCredentialsFromCloud(authState: AuthStateModel): CredentialsListResModel {
        try {
            val res = apiService.getAllCredentials(authState.accessToken ?: "")

            if (res.isSuccessful) {
                val data = res.body() as List<SignedCredential>

                // Map to domain
                // Credential type is detected in mapper
                val domainCreds = data.map {
                    credentialMapper.map(it)
                }

                return CredentialsListResModel.Success(domainCreds)
            } else {
                return when (res.code()) {
                    401 -> {
                        CredentialsListResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    }
                    else -> {
                        CredentialsListResModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return CredentialsListResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return CredentialsListResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}