package by.alexandr7035.affinidi_id.data.model.reset_password

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class ConfirmPasswordResetModel {
    class Success(): ConfirmPasswordResetModel()

    class Fail(val errorType: ErrorType): ConfirmPasswordResetModel()
}