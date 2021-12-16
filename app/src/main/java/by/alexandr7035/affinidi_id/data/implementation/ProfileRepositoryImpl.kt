package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.ProfileRepository
import by.alexandr7035.affinidi_id.data.model.UserProfile
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(private val authDataStorage: AuthDataStorage):  ProfileRepository {
    override fun getProfile(): UserProfile {
        return UserProfile(
            userName = authDataStorage.getUserName() ?: "Unknown username",
            userDid = authDataStorage.getDid() ?: "Unknown DID"
        )
    }
}