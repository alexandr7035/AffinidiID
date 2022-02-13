package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.repository.ResetPasswordRepository
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.model.network.reset_password.ConfirmResetPasswordRequest
import by.alexandr7035.data.model.network.reset_password.InitializeResetPasswordRequest
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val apiCallHelper: ApiCallHelper
) : ResetPasswordRepository {

    override suspend fun initializePasswordReset(initializePasswordResetRequestModel: InitializePasswordResetRequestModel): InitializePasswordResetResponseModel {
        val res = apiCallHelper.executeCall {
            apiService.initializePasswordReset(InitializeResetPasswordRequest(initializePasswordResetRequestModel.userName))
        }

        return when (res) {
            is ApiCallWrapper.Success -> {
                InitializePasswordResetResponseModel.Success(initializePasswordResetRequestModel.userName)
            }
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    // FIXME wrong errortype for 400
                    400 -> InitializePasswordResetResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    404 -> InitializePasswordResetResponseModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> InitializePasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> InitializePasswordResetResponseModel.Fail(res.errorType)
        }
    }


    override suspend fun confirmResetPassword(confirmPasswordResetRequestModel: ConfirmPasswordResetRequestModel): ConfirmPasswordResetResponseModel {

        val res = apiCallHelper.executeCall {
            apiService.confirmPasswordReset(
                ConfirmResetPasswordRequest(
                    userName = confirmPasswordResetRequestModel.userName,
                    newPassword = confirmPasswordResetRequestModel.newPassword,
                    confirmationCode = confirmPasswordResetRequestModel.confirmationCode
                )
            )
        }

        return when (res) {
            is ApiCallWrapper.Success -> ConfirmPasswordResetResponseModel.Success
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    400 -> ConfirmPasswordResetResponseModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    // We can also get 404 error which means user doesn't exist.
                    // On code confirmation stage it's unintended behavior
                    // So we throw unknown error in this case to show it on UI
                    else -> ConfirmPasswordResetResponseModel.Fail(ErrorType.UNKNOWN_ERROR)
                    // TODO handle "too many requests" here
                }
            }
            is ApiCallWrapper.Fail -> {
                ConfirmPasswordResetResponseModel.Fail(res.errorType)
            }
        }
    }
}