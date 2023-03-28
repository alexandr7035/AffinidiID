package by.alexandr7035.affinidi_id.domain.model.signup

class ConfirmSignUpRequestModel(
    val userName: String,
    val confirmationToken: String,
    val confirmationCode: String
)