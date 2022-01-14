package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.SignInModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import by.alexandr7035.data.api.ApiService
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.model.sign_in.SignInRequest
import by.alexandr7035.data.model.sign_in.SignInResponse
import by.alexandr7035.data.storage.SecretsStorage
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(private val apiService: ApiService, private val secretsStorage: SecretsStorage): LoginRepository {
    override suspend fun signIn(userName: String, password: String): SignInModel {

        try {
            val res = apiService.signIn(SignInRequest(userName, password))
            if (res.isSuccessful) {
                val data = res.body() as SignInResponse

                secretsStorage.saveAccessToken(data.accessToken)

                return SignInModel.Success(
                    userDid = data.userDid,
                    userName = userName
                )
            }
            else {
                return when (res.code()) {
                    400 -> {
                        SignInModel.Fail(ErrorType.WRONG_USERNAME_OR_PASSWORD)
                    }
                    404 -> {
                        SignInModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    }
                    // Unknown fail code
                    else -> {
                        SignInModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return SignInModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return SignInModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override fun saveAccessToken(accessToken: String?) {
        secretsStorage.saveAccessToken(accessToken)
    }

    override fun getAccessToken(): String? {
        return secretsStorage.getAccessToken()
    }
}