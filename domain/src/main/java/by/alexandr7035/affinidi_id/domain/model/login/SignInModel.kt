package by.alexandr7035.affinidi_id.domain.model.login

import by.alexandr7035.affinidi_id.domain.core.ErrorType


sealed class SignInModel {
    data class Success(
        val userDid: String,
        val userName: String
    ): SignInModel()

    data class Fail(
        val errorType: ErrorType
    ): SignInModel()
}