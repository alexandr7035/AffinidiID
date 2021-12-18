package by.alexandr7035.affinidi_id.data.model.sign_up

import com.google.gson.annotations.SerializedName

data class ConfirmSignUpResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("did")
    val userDid: String
)