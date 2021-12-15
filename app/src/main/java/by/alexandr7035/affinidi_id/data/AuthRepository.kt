package by.alexandr7035.affinidi_id.data

interface AuthRepository {
    suspend fun signUp(
        userName: String,
        password: String,
    ): SignUpModel
}