package by.alexandr7035.affinidi_id.domain.repository

interface AppSettings {
    fun isAppLockedWithBiometrics(): Boolean

    fun setAppLockedWithBiometrics(locked: Boolean)
}