package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.model.SignUpModel
import by.alexandr7035.affinidi_id.data.model.SignInRequest
import by.alexandr7035.affinidi_id.data.model.SignInModel
import by.alexandr7035.affinidi_id.data.model.SignInResponse
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStorage: AuthDataStorage
): AuthRepository {
    override suspend fun signUp(userName: String, password: String): SignUpModel {

        // FIXME test only
        return SignUpModel.Fail(ErrorType.USER_ALREADY_EXISTS)
    }

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

    private fun saveAuthData(userDid: String, accessToken: String) {
        authDataStorage.saveDid(userDid)
        authDataStorage.saveAccessToken(accessToken)
    }
}