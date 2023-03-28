package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.model.DataCredentialsList
import javax.inject.Inject

class CredentialsCloudDataSourceImpl @Inject constructor(
    private val apiService: CredentialsApiService,
    private val apiCallHelper: ApiCallHelper,
    private val appSettings: AppSettings
) :
    CredentialsCloudDataSource {
    override suspend fun getCredentialsFromCloud(): DataCredentialsList {

        val res = apiCallHelper.executeCall {
            apiService.getAllCredentials(appSettings.getAuthCredentials().accessToken)
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                DataCredentialsList.Success(res.data)
            }
            is ApiCallWrapper.Fail -> DataCredentialsList.Fail(res.errorType)
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    401 -> DataCredentialsList.Fail(ErrorType.AUTH_SESSION_EXPIRED)
                    else -> DataCredentialsList.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
    }
}