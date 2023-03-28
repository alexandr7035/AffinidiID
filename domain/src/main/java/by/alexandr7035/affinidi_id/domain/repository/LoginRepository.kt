package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel

interface LoginRepository {
    suspend fun signIn(
        userName: String,
        password: String,
    ): GenericRes<Unit>

    suspend fun signInWithRefreshToken(): GenericRes<Unit>

    suspend fun logOut(): LogOutModel
}