package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import by.alexandr7035.affinidi_id.data.model.profile.UserProfileModel

interface ProfileRepository {
    fun getProfile(): UserProfileModel

    fun getProfileImageUrl(userDid: String): String
}