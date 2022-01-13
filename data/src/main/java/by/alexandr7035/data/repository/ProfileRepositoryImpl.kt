package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.model.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository

class ProfileRepositoryImpl: ProfileRepository {
    override fun getProfile(): UserProfile {
        // TODO FIXME
        return UserProfile(
            userName = "com@example.com",
            userDid = "did:elem:testdid123456789",
            imageUrl = "https://image.com"
        )
    }
}