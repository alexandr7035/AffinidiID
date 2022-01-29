package by.alexandr7035.affinidi_id.domain.model.reset_password

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class InitializePasswordResetResponseModel {
    data class Success(val userName: String): InitializePasswordResetResponseModel()

    data class Fail(val errorType: ErrorType): InitializePasswordResetResponseModel()
}