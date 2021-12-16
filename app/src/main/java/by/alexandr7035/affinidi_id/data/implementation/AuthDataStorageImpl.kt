package by.alexandr7035.affinidi_id.data.implementation

import android.app.Application
import android.content.Context
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import javax.inject.Inject

class AuthDataStorageImpl @Inject constructor(private val application: Application): AuthDataStorage {

    private val prefs = application.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveDid(userDid: String?) {
        prefs.edit().putString(USER_DID_STR, userDid).apply()
    }

    override fun getDid(): String? {
        return prefs.getString(USER_DID_STR, null)
    }

    override fun saveAccessToken(accessToken: String?) {
        prefs.edit().putString(ACCESS_TOKEN_STR, accessToken).apply()
    }

    override fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_STR, null)
    }

    companion object {
        private const val PREFERENCES_NAME = "AUTH_DATA_PREFS"
        private val ACCESS_TOKEN_STR = "ACCESS_TOKEN"
        private val USER_DID_STR = "USER_DID"
    }
}