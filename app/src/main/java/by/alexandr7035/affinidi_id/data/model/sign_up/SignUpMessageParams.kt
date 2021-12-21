package by.alexandr7035.affinidi_id.data.model.sign_up

import com.google.gson.annotations.SerializedName

data class SignUpMessageParams(
    @SerializedName("message")
    val message: String = "Sign Up new user"
)