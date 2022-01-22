package by.alexandr7035.data.model

import com.google.gson.annotations.SerializedName

data class CredentialHolder(
    @SerializedName("id")
    val holderDid: String
)