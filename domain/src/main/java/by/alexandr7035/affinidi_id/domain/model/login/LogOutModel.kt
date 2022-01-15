package by.alexandr7035.affinidi_id.domain.model.login

import by.alexandr7035.affinidi_id.domain.core.ErrorType

abstract class LogOutModel {
    class Success(): LogOutModel()

    data class Fail(
        val errorType: ErrorType
    ): LogOutModel()
}