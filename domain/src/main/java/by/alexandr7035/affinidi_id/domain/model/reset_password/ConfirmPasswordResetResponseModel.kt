package by.alexandr7035.affinidi_id.domain.model.reset_password

import by.alexandr7035.affinidi_id.domain.core.ErrorType

abstract class ConfirmPasswordResetResponseModel {
    class Success(): ConfirmPasswordResetResponseModel()

    class Fail(val errorType: ErrorType): ConfirmPasswordResetResponseModel()
}