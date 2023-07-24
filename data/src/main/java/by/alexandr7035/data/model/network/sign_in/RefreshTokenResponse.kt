package by.alexandr7035.data.model.network.sign_in

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(
    @SerializedName("accessToken")
    val accessToken: String,
)
