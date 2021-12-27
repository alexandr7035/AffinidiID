package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel

interface RegistrationRepository {
    suspend fun signUp(
        userName: String,
        password: String,
    ): SignUpModel

    suspend fun confirmSignUp(
        confirmationToken: String,
        confirmationCode: String
    ): SignUpConfirmationModel

    // FIXME refactoring
    fun saveUserName(userName: String)
}