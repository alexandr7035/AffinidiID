package by.alexandr7035.data.datasource.cache.app

import by.alexandr7035.affinidi_id.domain.model.login.AuthCredentials
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import com.cioccarellia.ksprefs.KsPrefs

class AppSettingsImpl(
    private val prefs: KsPrefs
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
            userDid = prefs.pull(key = SettingsKeys.USER_DID.name),
            userEmail = prefs.pull(key = SettingsKeys.USER_EMAIL.name),
            accessToken = prefs.pull(key = SettingsKeys.ACCESS_TOKEN.name),
            refreshToken = prefs.pull(key = SettingsKeys.REFRESH_TOKEN.name),
            accessTokenObtained = prefs.pull(key = SettingsKeys.ACCESS_TOKEN_OBTAINED.name)
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

}