package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.LoginRepository
import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInModel
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInRequest
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInResponse
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStorage: AuthDataStorage
): LoginRepository {
    override suspend fun signIn(userName: String, password: String): SignInModel {

        try {
            val res = apiService.signIn(SignInRequest(userName, password))
            if (res.isSuccessful) {
                val data = res.body() as SignInResponse

                // TODO think if should do this through fragment - viewmodel - repo chain
                saveAuthData(userDid = data.userDid, accessToken = data.accessToken)

                return SignInModel.Success(data.accessToken, data.userDid)
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

    override fun checkIfAuthorized(): Boolean {
        return authDataStorage.getAccessToken() != null
    }

    override fun saveUserName(userName: String) {
        authDataStorage.saveUserName(userName)
    }

    override suspend fun logOut(): LogOutModel {
        try {
            val res = apiService.logOut(authDataStorage.getAccessToken() ?: "")

            if (res.isSuccessful) {
                Timber.debug("LOGOUT SUCCESSFUL")

                authDataStorage.saveUserName(null)
                authDataStorage.saveAccessToken(null)
                authDataStorage.saveDid(null)

                return LogOutModel.Success()
            }
            else {
                return when (res.code()) {
                    401 -> {
                        // Authorization token already unactual
                        // Just clear it and return success logout
                        authDataStorage.saveUserName(null)
                        authDataStorage.saveAccessToken(null)
                        authDataStorage.saveDid(null)
                        return LogOutModel.Success()
                    }
                    else -> {
                        LogOutModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            appError.printStackTrace()
            Timber.debug("ERRORTYPE ${appError.errorType.name}")
            return LogOutModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            Timber.debug("LOGOUT FAILED other exception $e")
            e.printStackTrace()
            return LogOutModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    private fun saveAuthData(userDid: String, accessToken: String) {
        authDataStorage.saveDid(userDid)
        authDataStorage.saveAccessToken(accessToken)
    }
}