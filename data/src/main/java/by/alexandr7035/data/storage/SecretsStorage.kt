package by.alexandr7035.data.storage

interface SecretsStorage {
    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?
}