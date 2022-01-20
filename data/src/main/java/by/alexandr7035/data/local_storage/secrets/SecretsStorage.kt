package by.alexandr7035.data.local_storage.secrets

interface SecretsStorage {
    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?
}