package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.DicebearAvatarsHelper
import by.alexandr7035.affinidi_id.data.DicebearImageType
import by.alexandr7035.affinidi_id.data.ProfileRepository
import by.alexandr7035.affinidi_id.data.model.UserProfile
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage,
    private val avatarsHelper: DicebearAvatarsHelper
):  ProfileRepository {
    override fun getProfile(): UserProfile {
        return UserProfile(
            userName = authDataStorage.getUserName() ?: "Unknown username",
            userDid = authDataStorage.getDid() ?: "Unknown DID"
        )
    }

    override fun getProfileImageUrl(userDid: String): String {

        // Did contains ":" symbol which can't be used in urls
        // So split did and get last part
        // Also take last 25 symbols as elem DID may be extremely long
        val uniqueString = userDid.split(":").last().takeLast(25)

        return avatarsHelper.getImageUrl(
            imageType = DicebearImageType.AVATAR_IDENTICON,
            dataString = uniqueString,
            imageSize = 1
        )
    }
}