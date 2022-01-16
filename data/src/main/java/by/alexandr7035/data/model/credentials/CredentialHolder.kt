package by.alexandr7035.data.model.credentials

import com.google.gson.annotations.SerializedName

data class CredentialHolder(
    @SerializedName("id")
    val holderDid: String
)