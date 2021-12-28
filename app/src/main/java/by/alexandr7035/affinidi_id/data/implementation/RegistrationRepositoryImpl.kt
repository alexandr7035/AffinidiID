package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.RegistrationRepository
import by.alexandr7035.affinidi_id.data.model.sign_up.*
import timber.log.Timber
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStorage: AuthDataStorage
): RegistrationRepository {
    override suspend fun signUp(userName: String, password: String): SignUpModel {

        try {

            val res = apiService.signUp(
                SignUpRequest(
                    userName = userName,
                    password = password,
                    // Use with default params
                    signUpOptions = SignUpOptions(),
                    signUpMessageParams = SignUpMessageParams()
                )
            )

            return if (res.isSuccessful) {
                // Get token for signup confirmation and return
                SignUpModel.Success(res.body() as String)
            } else {
                Timber.debug("RES FAILED")
                when (res.code()) {
                    409 -> {
                        SignUpModel.Fail(ErrorType.USER_ALREADY_EXISTS)
                    }
                    else -> {
                        SignUpModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            Timber.debug("RES FAILED ${appError.errorType.name}")
            return SignUpModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return SignUpModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun confirmSignUp(confirmationToken: String, confirmationCode: String): SignUpConfirmationModel {
        try {
            val res = apiService.confirmSignUp(ConfirmSignUpRequest(
                token = confirmationToken,
                confirmationCode = confirmationCode
            ))

            if (res.isSuccessful) {
                val data = res.body() as ConfirmSignUpResponse

                // TODO think if should do this through fragment - viewmodel - repo chain
                saveAuthData(userDid = data.userDid, accessToken = data.accessToken)

                return SignUpConfirmationModel.Success(
                    accessToken = data.accessToken,
                    userDid = data.userDid
                )
            }
            else {
                return when (res.code()) {
                    400 -> {
                        SignUpConfirmationModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    }
                    else -> {
                        SignUpConfirmationModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return SignUpConfirmationModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return SignUpConfirmationModel.Fail(ErrorType.UNKNOWN_ERROR)
        }

    }

    override fun saveUserName(userName: String) {
        authDataStorage.saveUserName(userName)
    }

    private fun saveAuthData(userDid: String, accessToken: String) {
        authDataStorage.saveDid(userDid)
        authDataStorage.saveAccessToken(accessToken)
    }
}