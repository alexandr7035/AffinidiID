package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.model.login.SignInModel

interface LoginRepository {
    suspend fun signIn(
        userName: String,
        password: String,
    ): SignInModel

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

    suspend fun logOut(accessToken: String): LogOutModel
}