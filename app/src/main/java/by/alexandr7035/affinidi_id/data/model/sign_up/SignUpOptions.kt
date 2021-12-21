package by.alexandr7035.affinidi_id.data.model.sign_up

import com.google.gson.annotations.SerializedName

data class SignUpOptions(
    @SerializedName("didMethod")
    val didMethod: String = "elem",
    @SerializedName("keyTypes")
    val keyTypes: List<String> = listOf("rsa")
)
