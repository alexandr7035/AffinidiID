package by.alexandr7035.data.datasource.cache.secrets

interface SecretsStorage {
    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?
}