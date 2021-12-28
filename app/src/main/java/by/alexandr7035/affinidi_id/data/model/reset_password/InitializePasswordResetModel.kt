package by.alexandr7035.affinidi_id.data.model.reset_password

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class InitializePasswordResetModel {
    data class Success(val userName: String): InitializePasswordResetModel()

    data class Fail(val errorType: ErrorType): InitializePasswordResetModel()
}