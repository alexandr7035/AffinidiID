package by.alexandr7035.affinidi_id.data.model.sign_up

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class SignUpConfirmationModel {
    data class Success(
        val userDid: String,
        val accessToken: String
    ): SignUpConfirmationModel()

    data class Fail(
        val errorType: ErrorType
    ): SignUpConfirmationModel()
}