package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.AuthCheckRepository
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import javax.inject.Inject


// TODO move logic to other use case
class AuthCheckRepositoryImpl @Inject constructor(
    private val userApiService: UserApiService,
    private val apiCallHelper: ApiCallHelper
) : AuthCheckRepository {
    override suspend fun makeCheckRequest(authStateModel: AuthStateModel): AuthCheckResModel {

        // Make light api call to check if access token is still valid
        val res = apiCallHelper.executeCall {
            userApiService.getUserDID(authStateModel.accessToken ?: "")
        }

        return when (res) {
            is ApiCallWrapper.Success -> AuthCheckResModel.Success
            is ApiCallWrapper.Fail -> AuthCheckResModel.Fail(res.errorType)
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    401 -> AuthCheckResModel.Fail(ErrorType.AUTH_SESSION_EXPIRED)
                    else -> AuthCheckResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
    }
}