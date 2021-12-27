package by.alexandr7035.affinidi_id.data.model.reset_password

import com.google.gson.annotations.SerializedName

data class ConfirmResetPasswordRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("otp")
    val confirmationCode: String,
    @SerializedName("newPassword")
    val newPassword: String
)