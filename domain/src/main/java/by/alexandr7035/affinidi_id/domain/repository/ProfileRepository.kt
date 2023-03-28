package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile

interface ProfileRepository {
    fun getProfile(): UserProfile
}