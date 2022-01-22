package by.alexandr7035.data.model.network.profile

import com.google.gson.annotations.SerializedName

data class ChangePasswordRequest(
    @SerializedName("oldPassword")
    val oldPassword: String,
    @SerializedName("newPassword")
    val newPassword: String
)