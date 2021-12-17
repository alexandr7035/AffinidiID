package by.alexandr7035.affinidi_id.data.model

import com.google.gson.annotations.SerializedName

data class SignUpRequest(
    @SerializedName("username")
    val userName: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("options")
    val signUpOptions: SignUpOptions,
    @SerializedName("messageParameters")
    val signUpMessageParams: SignUpMessageParams
)