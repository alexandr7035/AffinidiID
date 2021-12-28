package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInModel

interface LoginRepository {
    suspend fun signIn(
        userName: String,
        password: String,
    ): SignInModel

    fun checkIfAuthorized(): Boolean

    fun saveUserName(userName: String)

    suspend fun logOut(): LogOutModel
}