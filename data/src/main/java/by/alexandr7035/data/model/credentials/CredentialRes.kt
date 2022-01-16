package by.alexandr7035.data.model.credentials

import com.google.gson.annotations.SerializedName

data class CredentialRes(
    @SerializedName("@context")
    val context: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: List<String>,
    @SerializedName("holder")
    val holder: CredentialHolder,
    @SerializedName("credentialSubject")
    val credentialSubject: CredentialSubject,
    @SerializedName("issuanceDate")
    val issuanceDate: String,
    @SerializedName("expirationDate")
    val expirationDate: String?,
    @SerializedName("issuer")
    val issuerDid: String,
    @SerializedName("proof")
    val proof: CredentialProof
)