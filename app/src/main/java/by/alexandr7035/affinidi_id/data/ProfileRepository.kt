package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.UserProfile

interface ProfileRepository {
    fun getProfile(): UserProfile
}