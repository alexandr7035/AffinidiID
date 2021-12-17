package by.alexandr7035.affinidi_id.data.model

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class LogOutModel {
    class Success(): LogOutModel()

    data class Fail(
        val errorType: ErrorType
    ): LogOutModel()
}