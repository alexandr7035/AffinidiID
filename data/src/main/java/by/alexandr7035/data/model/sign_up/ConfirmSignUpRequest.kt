package by.alexandr7035.data.model.sign_up

import com.google.gson.annotations.SerializedName

data class ConfirmSignUpRequest(
    @SerializedName("token")
    val token: String,
    @SerializedName("confirmationCode")
    val confirmationCode: String
)