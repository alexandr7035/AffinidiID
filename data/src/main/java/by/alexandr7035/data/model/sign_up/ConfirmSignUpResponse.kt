package by.alexandr7035.data.model.sign_up

import com.google.gson.annotations.SerializedName

data class ConfirmSignUpResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("did")
    val userDid: String
)