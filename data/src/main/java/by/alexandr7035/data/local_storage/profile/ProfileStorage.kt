package by.alexandr7035.data.local_storage.profile

interface ProfileStorage {
    fun saveDid(userDid: String?)

    fun getDid(): String?

    fun saveUserName(userName: String?)

    fun getUserName(): String?
}