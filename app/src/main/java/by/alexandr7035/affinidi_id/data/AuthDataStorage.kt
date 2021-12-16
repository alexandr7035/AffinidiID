package by.alexandr7035.affinidi_id.data

interface AuthDataStorage {
    fun saveDid(userDid: String?)

    fun getDid(): String?

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?
}