package by.alexandr7035.data.storage

import android.app.Application
import android.content.Context
import by.alexandr7035.data.extensions.debug
import timber.log.Timber
import javax.inject.Inject

class ProfileStorageImpl @Inject constructor(private val application: Application): ProfileStorage {

    private val prefs = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveDid(userDid: String?) {
        prefs.edit().putString(USER_DID_STR, userDid).apply()
    }

    override fun getDid(): String? {
        return prefs.getString(USER_DID_STR, null)
    }

    override fun saveUserName(userName: String?) {
        Timber.debug("USER NAME SAVE $userName")
        prefs.edit().putString(USER_NAME_STR, userName).apply()
    }

    override fun getUserName(): String? {
        return prefs.getString(USER_NAME_STR, null)
    }

    companion object {
        private const val PREFERENCES_NAME = "PROFILE_DATA_PREFS"
        private const val USER_DID_STR = "USER_DID"
        private const val USER_NAME_STR = "USER_NAME"
    }
}