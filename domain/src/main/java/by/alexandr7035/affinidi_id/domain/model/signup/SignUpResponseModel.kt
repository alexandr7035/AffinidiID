package by.alexandr7035.affinidi_id.domain.model.signup

import by.alexandr7035.affinidi_id.domain.core.ErrorType

abstract class SignUpResponseModel {
    data class Success(
        val confirmSignUpToken: String
    ): SignUpResponseModel()

    data class Fail(
        val errorType: ErrorType
    ): SignUpResponseModel()
}