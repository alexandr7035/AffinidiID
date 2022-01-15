package by.alexandr7035.data.model.reset_password

import com.google.gson.annotations.SerializedName

data class InitializeResetPasswordRequest(
    @SerializedName("username")
    val userName: String
)