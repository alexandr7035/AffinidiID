package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.LogOutModel
import by.alexandr7035.affinidi_id.data.model.UserProfile

interface ProfileRepository {
    fun getProfile(): UserProfile

    fun getProfileImageUrl(userDid: String): String

    suspend fun logOut(): LogOutModel
}