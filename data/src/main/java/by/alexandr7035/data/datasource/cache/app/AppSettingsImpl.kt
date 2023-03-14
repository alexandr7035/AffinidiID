package by.alexandr7035.data.datasource.cache.app

import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import com.cioccarellia.ksprefs.KsPrefs

class AppSettingsImpl(
    private val prefs: KsPrefs
): AppSettings {
    override fun isAppLockedWithBiometrics(): Boolean {
        return prefs.pull(key = SettingsKeys.APP_LOCKED_WITH_BIOMETRICS.name, fallback = false)
    }

    override fun setAppLockedWithBiometrics(locked: Boolean) {
        prefs.push(key = SettingsKeys.APP_LOCKED_WITH_BIOMETRICS.name, value = locked)
    }
}