package by.alexandr7035.data.model.credentials.unsigned_vc

import by.alexandr7035.affinidi_id.domain.model.credentials.credential_subject.CredentialSubject
import com.google.gson.annotations.SerializedName

data class BuildUnsignedVcReq(
    @SerializedName("credentialSubject")
    val credentialSubject: CredentialSubject,
    @SerializedName("typeName")
    val typeName: String,
    @SerializedName("holderDid")
    val holderDid: String,
    @SerializedName("expiresAt")
    val expirationDate: String?,
    @SerializedName("jsonLdContextUrl")
    val jsonLdContextUrl: String
)
