package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.alexandr7035.affinidi_id.domain.repository.RegistrationRepository
import by.alexandr7035.data.network.UserApiService
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.model.sign_up.*
import by.alexandr7035.data.storage.SecretsStorage
import javax.inject.Inject

class RegistrationRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val secretsStorage: SecretsStorage
) : RegistrationRepository {

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): SignUpResponseModel {
        try {
            val res = apiService.signUp(
                SignUpRequest(
                    userName = signUpRequestModel.userName,
                    password = signUpRequestModel.password,
                    // Use with default params
                    signUpOptions = SignUpOptions(),
                    signUpMessageParams = SignUpMessageParams()
                )
            )

            return if (res.isSuccessful) {
                // Get token for signup confirmation and return
                SignUpResponseModel.Success(res.body() as String)
            } else {
                when (res.code()) {
                    409 -> {
                        SignUpResponseModel.Fail(ErrorType.USER_ALREADY_EXISTS)
                    }
                    else -> {
                        SignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return SignUpResponseModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return SignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun confirmSignUp(confirmSignUpRequestModel: ConfirmSignUpRequestModel): ConfirmSignUpResponseModel {
        try {
            val res = apiService.confirmSignUp(ConfirmSignUpRequest(
                    token = confirmSignUpRequestModel.confirmationToken,
                    confirmationCode = confirmSignUpRequestModel.confirmationCode
                )
            )

            if (res.isSuccessful) {
                val data = res.body() as ConfirmSignUpResponse

                secretsStorage.saveAccessToken(data.accessToken)

                return ConfirmSignUpResponseModel.Success(
                    accessToken = data.accessToken,
                    userDid = data.userDid
                )
            } else {
                return when (res.code()) {
                    400 -> {
                        ConfirmSignUpResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    }
                    else -> {
                        ConfirmSignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ConfirmSignUpResponseModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return ConfirmSignUpResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}