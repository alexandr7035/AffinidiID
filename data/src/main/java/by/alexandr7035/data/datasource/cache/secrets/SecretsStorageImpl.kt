package by.alexandr7035.data.datasource.cache.secrets

import android.content.Context
import javax.inject.Inject

// TODO refactor implementation
class SecretsStorageImpl @Inject constructor(applicationContext: Context): SecretsStorage {

    private val prefs = applicationContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    override fun saveAccessToken(accessToken: String?) {
        prefs.edit().putString(ACCESS_TOKEN_STR, accessToken).apply()
    }

    override fun getAccessToken(): String? {
        return prefs.getString(ACCESS_TOKEN_STR, null)
    }

    companion object {
        private const val PREFERENCES_NAME = "SECRET_DATA_PREFS"
        private const val ACCESS_TOKEN_STR = "ACCESS_TOKEN"
    }
}