package by.alexandr7035.affinidi_id.domain.model.signup

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ConfirmSignUpResponseModel {
    data class Success(
        val userDid: String,
        val accessToken: String
    ): ConfirmSignUpResponseModel()

    data class Fail(
        val errorType: ErrorType
    ): ConfirmSignUpResponseModel()
}