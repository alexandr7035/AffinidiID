package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.model.profile.SaveProfileModel
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import by.alexandr7035.data.helpers.DicebearAvatarsHelper
import by.alexandr7035.data.model.profile.DicebearImageType
import by.alexandr7035.data.storage.ProfileStorage
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
        // Did contains ":" symbol which can't be used in urls
        // So split did and get last part
        // Also take last 25 symbols as elem DID may be extremely long
        val uniqueString = userDid.split(":").last().takeLast(25)
        val avatartUrl = avatarsHelper.getImageUrl(DicebearImageType.AVATAR_IDENTICON, uniqueString, 1)

        return UserProfile(
            userName = userName,
            userDid = userDid,
            imageUrl = avatartUrl
        )
    }

    override fun saveProfile(saveProfileModel: SaveProfileModel) {
        profileStorage.saveUserName(saveProfileModel.userName)
        profileStorage.saveDid(saveProfileModel.userDid)
    }
}