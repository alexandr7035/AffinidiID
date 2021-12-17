package by.alexandr7035.affinidi_id.data.model

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class SignUpModel {
    data class Success(
        val userDid: String
    ): SignUpModel()

    data class Fail(
        val errorType: ErrorType
    ): SignUpModel()
}