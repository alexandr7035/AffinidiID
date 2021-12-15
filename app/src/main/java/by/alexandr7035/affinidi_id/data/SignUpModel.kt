package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.core.ErrorType
import java.lang.Exception

abstract class SignUpModel {
    data class Success(
        val userDid: String
    ): SignUpModel()

    data class Fail(
        val errorType: ErrorType
    ): SignUpModel()
}