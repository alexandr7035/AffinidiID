package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.profile.DicebearImageType

interface DicebearAvatarsHelper {
    fun getImageUrl(
        // See all the possible types on the site
        // https://avatars.dicebear.com/
        imageType: DicebearImageType,
        // Unique image is generated depending on this data
        dataString: String,
        imageSize: Int
    ): String
}