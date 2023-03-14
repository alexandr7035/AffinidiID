package by.alexandr7035.data.helpers.profile_avatars

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