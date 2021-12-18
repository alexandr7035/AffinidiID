package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.sign_in.SignInModel
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel

interface AuthRepository {
    suspend fun signUp(
        userName: String,
        password: String,
    ): SignUpModel

    suspend fun confirmSignUp(
        confirmationToken: String,
        confirmationCode: String
    ): SignUpConfirmationModel

    suspend fun signIn(
        userName: String,
        password: String,
    ): SignInModel

    fun checkIfAuthorized(): Boolean

    fun saveUserName(userName: String)
}