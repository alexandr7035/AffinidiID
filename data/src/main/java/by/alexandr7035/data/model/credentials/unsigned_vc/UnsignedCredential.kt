package by.alexandr7035.data.model.credentials.unsigned_vc

import by.alexandr7035.data.model.credentials.common.CredentialHolder
import by.alexandr7035.data.model.credentials.common.UndefinedCredentialSubject
import com.google.gson.annotations.SerializedName

data class UnsignedCredential(
    @SerializedName("@context")
    val context: List<String>,
    @SerializedName("id")
    val id: String,
    @SerializedName("type")
    val type: List<String>,
    @SerializedName("holder")
    val holder: CredentialHolder,
    // The core property with VC data. Cast it to
    // required domain CredentialSubject model
    // depending on credentialType
    @SerializedName("credentialSubject")
    val credentialSubject: UndefinedCredentialSubject,
    @SerializedName("issuanceDate")
    val issuanceDate: String,
    @SerializedName("expirationDate")
    val expirationDate: String?,
)