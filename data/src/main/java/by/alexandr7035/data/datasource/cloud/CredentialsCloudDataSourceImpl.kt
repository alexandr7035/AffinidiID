package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.SignedCredential
import javax.inject.Inject

class CredentialsCloudDataSourceImpl @Inject constructor(
    private val apiService: CredentialsApiService,
    private val apiCallHelper: ApiCallHelper
) :
    CredentialsCloudDataSource {
    override suspend fun getCredentialsFromCloud(authState: AuthStateModel): DataCredentialsList {

        val res = apiCallHelper.executeCall() {
            apiService.getAllCredentials(authState.accessToken ?: "")
        }

        return when (res) {
            is ApiCallWrapper.Success<*> -> {
                val credentials = res.data as List<SignedCredential>
                DataCredentialsList.Success(credentials)
            }
            is ApiCallWrapper.Fail -> DataCredentialsList.Fail(res.errorType)
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    401 -> DataCredentialsList.Fail(ErrorType.AUTHORIZATION_ERROR)
                    else -> DataCredentialsList.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
    }
}