package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.repository.ResetPasswordRepository
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.model.network.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.data.model.network.reset_password.InitializeResetPasswordRequest
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(private val apiService: UserApiService) : ResetPasswordRepository {
    override suspend fun initializePasswordReset(initializePasswordResetRequestModel: InitializePasswordResetRequestModel): InitializePasswordResetResponseModel {
        try {
            val res = apiService.initializePasswordReset(InitializeResetPasswordRequest(initializePasswordResetRequestModel.userName))

            return if (res.isSuccessful) {
                InitializePasswordResetResponseModel.Success(initializePasswordResetRequestModel.userName)
            } else {
                when (res.code()) {
                    // FIXME wrong errortype for 400
                    400 -> InitializePasswordResetResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    404 -> InitializePasswordResetResponseModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> InitializePasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return InitializePasswordResetResponseModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return InitializePasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun confirmResetPassword(confirmPasswordResetRequestModel: ConfirmPasswordResetRequestModel): ConfirmPasswordResetResponseModel {
        try {
            val res = apiService.confirmPasswordReset(
                ConfirmResetPasswordRequest(
                    userName = confirmPasswordResetRequestModel.userName,
                    newPassword = confirmPasswordResetRequestModel.newPassword,
                    confirmationCode = confirmPasswordResetRequestModel.confirmationCode
                )
            )

            return if (res.isSuccessful) {
                ConfirmPasswordResetResponseModel.Success()
            } else {
                when (res.code()) {
                    400 -> ConfirmPasswordResetResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    // We can also get 404 error which means user doesn't exist.
                    // On code confirmation stage it's unintended behavior
                    // So we throw unknown error in this case to show it on UI
                    else -> ConfirmPasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                    // TODO handle "too many requests" here
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ConfirmPasswordResetResponseModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return ConfirmPasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}