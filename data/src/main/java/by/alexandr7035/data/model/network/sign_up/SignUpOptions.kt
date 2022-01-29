package by.alexandr7035.data.model.network.sign_up

import com.google.gson.annotations.SerializedName

data class SignUpOptions(
    @SerializedName("didMethod")
    val didMethod: String = "elem",
    @SerializedName("keyTypes")
    val keyTypes: List<String> = listOf("rsa")
)
