package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.login.AuthCredentials
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile

interface AppSettings {
    // Auth credentials
    fun setAuthCredentials(authCredentials: AuthCredentials)

    fun getAuthCredentials(): AuthCredentials

    fun updateAuthCredentials(accessToken: String, accessTokenRefreshedDate: Long)

    // Profile
    fun getProfile(): UserProfile

    // App lock
    fun isAppLockedWithBiometrics(): Boolean

    fun setAppLockedWithBiometrics(locked: Boolean)
}