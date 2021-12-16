package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.SignInModel

interface AuthRepository {
    suspend fun signUp(
        userName: String,
        password: String,
    ): SignUpModel

    suspend fun signIn(
        userName: String,
        password: String,
    ): SignInModel

    fun checkIfAuthorized(): Boolean

    fun saveUserName(userName: String)
}