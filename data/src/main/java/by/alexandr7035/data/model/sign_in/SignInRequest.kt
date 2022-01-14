package by.alexandr7035.data.model.sign_in

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String,
)