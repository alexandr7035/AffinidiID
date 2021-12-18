package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.*
import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import by.alexandr7035.affinidi_id.data.model.profile.UserProfileModel
import by.alexandr7035.affinidi_id.data.model.profile.DicebearImageType
import timber.log.Timber
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val authDataStorage: AuthDataStorage,
    private val avatarsHelper: DicebearAvatarsHelper,
    private val apiService: ApiService
) : ProfileRepository {
    override fun getProfile(): UserProfileModel {
        return UserProfileModel(
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