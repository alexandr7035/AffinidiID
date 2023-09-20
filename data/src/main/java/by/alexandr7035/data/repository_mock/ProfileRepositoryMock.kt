package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.ProfileRepository
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.profile_avatars.DicebearImageType

class ProfileRepositoryMock(
    private val avatarsHelper: DicebearAvatarsHelper
): ProfileRepository {
    override fun getProfile(): UserProfile {
        return UserProfile(
            userDid = MockConstants.MOCK_DID,
            userName = MockConstants.MOCK_LOGIN,
            imageUrl = avatarsHelper.getImageUrl(
                imageSize = 1,
                imageType = DicebearImageType.AVATAR_IDENTICON,
                dataString = MockConstants.MOCK_DID
            )
        )
    }
}