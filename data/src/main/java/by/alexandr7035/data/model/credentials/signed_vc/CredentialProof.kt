package by.alexandr7035.data.model.credentials.signed_vc

import com.google.gson.annotations.SerializedName

// FIXME multiple proof types may exist
data class CredentialProof(
    @SerializedName("type")
    val type: String,
    @SerializedName("created")
    val creationDate: String,
    @SerializedName("verificationMethod")
    val verificationMethod: String,
    @SerializedName("proofPurpose")
    val proofPurpose: String,
    @SerializedName("jws")
    val jws: String
)