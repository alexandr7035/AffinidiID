package by.alexandr7035.data.model.network.sign_in

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("did")
    val userDid: String,
)