package by.alexandr7035.affinidi_id.data.model.reset_password

import com.google.gson.annotations.SerializedName

data class InitializeResetPasswordRequest(
    @SerializedName("username")
    val userName: String
)