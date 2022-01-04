package by.alexandr7035.affinidi_id.data.model.change_username

import com.google.gson.annotations.SerializedName

data class ConfirmChangeUserNameRequest(
    @SerializedName("username")
    val newUserName: String,
    @SerializedName("confirmationCode")
    val confirmationCode: String
)