package by.alexandr7035.data.model.credentials.common

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class CredentialSubject(
    @SerializedName("data")
    val data: JSONObject
)