package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.helpers.vc_mapping.SignedCredentialToDomainMapper
import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.SignedCredential
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import javax.inject.Inject

class CredentialsCloudDataSourceImpl @Inject constructor(private val apiService: CredentialsApiService, private val credentialMapper: SignedCredentialToDomainMapper):
    CredentialsCloudDataSource {
    override suspend fun getCredentialsFromCloud(authState: AuthStateModel): DataCredentialsList {
        try {
            val res = apiService.getAllCredentials(authState.accessToken ?: "")

            if (res.isSuccessful) {
                val signedCredentials = res.body() as List<SignedCredential>

//                // Map to domain
//                // Credential type is detected in mapper
//                val domainCreds = data.map {
//                    credentialMapper.map(it)
//                }

                return DataCredentialsList.Success(signedCredentials)
            } else {
                return when (res.code()) {
                    401 -> {
                        DataCredentialsList.Fail(ErrorType.AUTHORIZATION_ERROR)
                    }
                    else -> {
                        DataCredentialsList.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return DataCredentialsList.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return DataCredentialsList.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}