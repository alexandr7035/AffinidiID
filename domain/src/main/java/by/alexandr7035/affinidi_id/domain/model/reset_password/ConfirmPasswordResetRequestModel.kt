package by.alexandr7035.affinidi_id.domain.model.reset_password

class ConfirmPasswordResetRequestModel(
    val userName: String,
    val confirmationCode: String,
    val newPassword: String
)