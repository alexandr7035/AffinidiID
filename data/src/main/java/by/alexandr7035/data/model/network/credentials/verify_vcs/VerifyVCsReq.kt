package by.alexandr7035.data.model.network.credentials.verify_vcs

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

data class VerifyVCsReq(
    // List of raw VCs
    @SerializedName("verifiableCredentials")
    val credentials: List<JsonObject>
)