package by.alexandr7035.affinidi_id.presentation.common.permissions

import android.content.Context
import javax.inject.Inject

class PermissionsPreferencesImpl @Inject constructor(applicationContext: Context) : PermissionsPreferences {

    private val prefs = applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun savePermissionAskedFlag(permission: String) {
        prefs.edit().putBoolean(permission, true).apply()
    }

    override fun checkIfPermissionPreviouslyAsked(permission: String): Boolean {
        return prefs.getBoolean(permission, false)
    }

    companion object {
        private const val PREFERENCES_NAME = "PERMISSIONS_PREFS"
    }
}