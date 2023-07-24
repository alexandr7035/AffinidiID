package by.alexandr7035.affinidi_id.domain.model.login

data class AuthCredentials(
    val userDid: String,
    val userEmail: String,
    val accessToken: String,
    val refreshToken: String,
    val accessTokenObtained: Long
)
