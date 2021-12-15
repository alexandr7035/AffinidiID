package by.alexandr7035.affinidi_id.data.model

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class SignInModel {
    data class Success(
        val userDid: String,
        val accessToken: String
    ): SignInModel()

    data class Fail(
        val errorType: ErrorType
    ): SignInModel()
}