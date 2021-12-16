package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.DicebearAvatarsHelper
import by.alexandr7035.affinidi_id.data.DicebearImageType
import timber.log.Timber

class DicebearAvatarsHelperImpl: DicebearAvatarsHelper {
    override fun getImageUrl(imageType: DicebearImageType, dataString: String, imageSize: Int): String {

        Timber.debug("AVATAR SEED $dataString")

        // Implement other avatar types here
        val typeString = when (imageType) {
            DicebearImageType.AVATAR_IDENTICON -> {
                IDENTICON_TYPE_STR
            }
            DicebearImageType.AVATAR_JDENTICON -> {
                JDENTICON_TYPE_STR
            }
        }

        return "$BASE_URL/$typeString/:$dataString.svg?size=$imageSize"
    }

    companion object {
        private const val BASE_URL = "https://avatars.dicebear.com/api"
        private const val JDENTICON_TYPE_STR = "jdenticon"
        private const val IDENTICON_TYPE_STR = "identicon"
    }
}