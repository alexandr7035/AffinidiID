package by.alexandr7035.affinidi_id.data.model

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String,
)