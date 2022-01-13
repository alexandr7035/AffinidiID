package by.alexandr7035.data.storage

interface AuthDataStorage {
    fun saveDid(userDid: String?)

    fun getDid(): String?

    fun saveAccessToken(accessToken: String?)

    fun getAccessToken(): String?

    fun saveUserName(userName: String?)

    fun getUserName(): String?
}