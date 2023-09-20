package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.repository.ResetPasswordRepository
import kotlinx.coroutines.delay

class ResetPasswordRepositoryMock : ResetPasswordRepository {
    override suspend fun initializePasswordReset(
        initializePasswordResetRequestModel: InitializePasswordResetRequestModel
    ): InitializePasswordResetResponseModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return if (initializePasswordResetRequestModel.userName == MockConstants.MOCK_LOGIN) {
            InitializePasswordResetResponseModel.Success(MockConstants.MOCK_LOGIN)
        } else {
            InitializePasswordResetResponseModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
        }
    }

    override suspend fun confirmResetPassword(
        confirmPasswordResetRequestModel: ConfirmPasswordResetRequestModel
    ): ConfirmPasswordResetResponseModel {
        return if (confirmPasswordResetRequestModel.userName === MockConstants.MOCK_LOGIN) {
           ConfirmPasswordResetResponseModel.Success
        } else {
            ConfirmPasswordResetResponseModel.Fail(ErrorType.WRONG_USERNAME_OR_PASSWORD)
        }
    }
}