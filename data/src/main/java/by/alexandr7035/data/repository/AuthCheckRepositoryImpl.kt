package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.AuthCheckRepository
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.network.UserApiService
import javax.inject.Inject

class AuthCheckRepositoryImpl @Inject constructor(private val userApiService: UserApiService): AuthCheckRepository {
    override suspend fun makeCheckRequest(authStateModel: AuthStateModel): AuthCheckResModel {
        try {
            val res = userApiService.getUserDID(authStateModel.accessToken ?: "")

            return if (res.isSuccessful) {
                AuthCheckResModel.Success()
            }
            else {
                when (res.code()) {
                    401 -> {
                        AuthCheckResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    }
                    else -> {
                        AuthCheckResModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return AuthCheckResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return AuthCheckResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}