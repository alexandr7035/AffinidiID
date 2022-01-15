package by.alexandr7035.data.storage

interface ProfileStorage {
    fun saveDid(userDid: String?)

    fun getDid(): String?

    fun saveUserName(userName: String?)

    fun getUserName(): String?
}