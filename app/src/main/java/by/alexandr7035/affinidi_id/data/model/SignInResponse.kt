package by.alexandr7035.affinidi_id.data.model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("did")
    val userDid: String,
)