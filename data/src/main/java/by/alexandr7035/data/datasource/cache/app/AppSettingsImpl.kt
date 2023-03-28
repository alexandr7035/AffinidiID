package by.alexandr7035.data.datasource.cache.app

import by.alexandr7035.affinidi_id.domain.model.login.AuthCredentials
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.data.helpers.profile_avatars.DicebearAvatarsHelper
import by.alexandr7035.data.helpers.profile_avatars.DicebearImageType
import com.cioccarellia.ksprefs.KsPrefs

class AppSettingsImpl(
    private val prefs: KsPrefs,
    private val avatarsHelper: DicebearAvatarsHelper,
    ) : AppSettings {

    override fun setAuthCredentials(authCredentials: AuthCredentials) {
        prefs.push(key = SettingsKeys.USER_DID.name, value = authCredentials.userDid)
        prefs.push(key = SettingsKeys.USER_EMAIL.name, value = authCredentials.userEmail)
        prefs.push(key = SettingsKeys.ACCESS_TOKEN.name, value = authCredentials.accessToken)
        prefs.push(key = SettingsKeys.REFRESH_TOKEN.name, value = authCredentials.refreshToken)
        prefs.push(key = SettingsKeys.ACCESS_TOKEN_OBTAINED.name, value = authCredentials.accessTokenObtained)
    }

    override fun getAuthCredentials(): AuthCredentials {
        return AuthCredentials(
            userDid = prefs.pull(key = SettingsKeys.USER_DID.name, fallback = ""),
            userEmail = prefs.pull(key = SettingsKeys.USER_EMAIL.name, fallback = ""),
            accessToken = prefs.pull(key = SettingsKeys.ACCESS_TOKEN.name, fallback = ""),
            refreshToken = prefs.pull(key = SettingsKeys.REFRESH_TOKEN.name, fallback = ""),
            accessTokenObtained = prefs.pull(key = SettingsKeys.ACCESS_TOKEN_OBTAINED.name, 0)
        )
    }

    override fun getProfile(): UserProfile {
        // Gen avatar picture by did
        // Did contains ":" symbol which can't be used in urls
        // So split did and get last part
        // Also take last 25 symbols as elem DID may be extremely long
        val userDid = prefs.pull<String>(key = SettingsKeys.USER_DID.name, fallback = "")
        val uniqueString = userDid.split(":").last().takeLast(25)
        val avatartUrl = avatarsHelper.getImageUrl(DicebearImageType.AVATAR_IDENTICON, uniqueString, 1)

        return UserProfile(
            userDid = userDid,
            userName = prefs.pull(key = SettingsKeys.USER_EMAIL.name, fallback = ""),
            imageUrl = avatartUrl
        )
    }

    override fun updateAuthCredentials(accessToken: String, accessTokenRefreshedDate: Long) {
        prefs.push(key = SettingsKeys.ACCESS_TOKEN.name, value = accessToken)
        prefs.push(key = SettingsKeys.ACCESS_TOKEN_OBTAINED.name, value = accessTokenRefreshedDate)
    }

    override fun isAppLockedWithBiometrics(): Boolean {
        return prefs.pull(key = SettingsKeys.APP_LOCKED_WITH_BIOMETRICS.name, fallback = false)
    }

    override fun setAppLockedWithBiometrics(locked: Boolean) {
        prefs.push(key = SettingsKeys.APP_LOCKED_WITH_BIOMETRICS.name, value = locked)
    }

    override fun clearSettings() {
        prefs.clear()
    }
}