package by.alexandr7035.affinidi_id.domain.model.signup

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class SignUpResponseModel {
    data class Success(
        val confirmSignUpToken: String,
        val userName: String
    ): SignUpResponseModel()

    data class Fail(
        val errorType: ErrorType
    ): SignUpResponseModel()
}