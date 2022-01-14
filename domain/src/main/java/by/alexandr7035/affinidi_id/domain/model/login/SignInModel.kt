package by.alexandr7035.affinidi_id.domain.model.login

import by.alexandr7035.affinidi_id.domain.core.ErrorType


abstract class SignInModel {
    data class Success(
        val userDid: String,
        val userName: String,
        val accessToken: String
    ): SignInModel()

    data class Fail(
        val errorType: ErrorType
    ): SignInModel()
}