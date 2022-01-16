package by.alexandr7035.data.model.credentials.common

import com.google.gson.annotations.SerializedName

data class CredentialHolder(
    @SerializedName("id")
    val holderDid: String
)