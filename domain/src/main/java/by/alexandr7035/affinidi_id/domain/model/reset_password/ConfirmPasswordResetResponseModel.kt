package by.alexandr7035.affinidi_id.domain.model.reset_password

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ConfirmPasswordResetResponseModel {
    object Success : ConfirmPasswordResetResponseModel()

    class Fail(val errorType: ErrorType): ConfirmPasswordResetResponseModel()
}