package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.ResetPasswordRepository
import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmPasswordResetModel
import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializePasswordResetModel
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializeResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(private val apiService: ApiService): ResetPasswordRepository{
    override suspend fun initializePasswordReset(userName: String): InitializePasswordResetModel {
        try {
            val res = apiService.initializePasswordReset(InitializeResetPasswordRequest(userName))

            return if (res.isSuccessful) {
                InitializePasswordResetModel.Success(userName)
            } else {
                when (res.code()) {
                    // FIXME wrong errortype for 400
                    400 -> InitializePasswordResetModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    404 -> InitializePasswordResetModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> InitializePasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return InitializePasswordResetModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return InitializePasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }


    override suspend fun confirmResetPassword(userName: String, newPassword: String, confirmationCode: String): ConfirmPasswordResetModel {
        try {
            val res = apiService.confirmPasswordReset(ConfirmResetPasswordRequest(
                userName = userName,
                newPassword = newPassword,
                confirmationCode = confirmationCode
            ))

            return if (res.isSuccessful) {
                ConfirmPasswordResetModel.Success()
            } else {
                when (res.code()) {
                    400 -> ConfirmPasswordResetModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    // We can also get 404 error which means user doesn't exist.
                    // On code confirmation stage it's unintended behavior
                    // So we throw unknown error in this case to show it on UI
                    else -> ConfirmPasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
                    // TODO handle "too many requests" here
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ConfirmPasswordResetModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return ConfirmPasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

}