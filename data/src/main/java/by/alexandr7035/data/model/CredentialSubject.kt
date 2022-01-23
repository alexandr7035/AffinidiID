package by.alexandr7035.data.model

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class CredentialSubject(
    // Credential subject data json ("data" : {...})
    @SerializedName("data")
    val data: JsonObject
)