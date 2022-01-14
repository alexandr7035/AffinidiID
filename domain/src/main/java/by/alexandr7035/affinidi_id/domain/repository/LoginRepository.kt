package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.login.SignInModel

interface LoginRepository {
    suspend fun signIn(
        userName: String,
        password: String,
    ): SignInModel

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

//    fun checkIfAuthorized(): Boolean
//
//    fun saveUserName(userName: String)
//
//    suspend fun logOut(): LogOutModel
}