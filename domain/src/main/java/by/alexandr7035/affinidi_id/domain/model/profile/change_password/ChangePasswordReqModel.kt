package by.alexandr7035.affinidi_id.domain.model.profile.change_password

data class ChangePasswordReqModel(
    val oldPassword: String,
    val newPassword: String
)