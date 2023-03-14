package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.model.profile.SaveProfileModel
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelper
import by.alexandr7035.data.model.local.profile.DicebearImageType
import by.alexandr7035.data.datasource.cache.profile.ProfileStorage
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val profileStorage: ProfileStorage,
    private val avatarsHelper: DicebearAvatarsHelper,
//    private val apiService: ApiService
): ProfileRepository {
    override fun getProfile(): UserProfile {
        // TODO FIXME

        val userName = profileStorage.getUserName() ?: "Unknown username"
        val userDid = profileStorage.getDid() ?: "Unknown DID"


        return UserProfile(
            userName = userName,
            userDid = userDid,
            imageUrl = ""
        )
    }

    override fun saveProfile(saveProfileModel: SaveProfileModel) {
        profileStorage.saveUserName(saveProfileModel.userName)
        profileStorage.saveDid(saveProfileModel.userDid)
    }

    override fun clearProfile() {
        profileStorage.saveUserName(null)
        profileStorage.saveDid(null)
    }
}