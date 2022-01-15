package by.alexandr7035.affinidi_id.domain.model.login

data class AuthStateModel(
    val isAuthorized: Boolean,
    val accessToken: String?
)