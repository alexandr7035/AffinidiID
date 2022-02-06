package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

data class Credential(
    val vcType: String,
    // JSON data
    val credentialSubjectData: String,
    val id: String,
    val holderDid: String,
    val issuerDid: String,
    val issuanceDate: Long,
    val expirationDate: Long?,
    val credentialStatus: CredentialStatus,
    val credentialProof: CredentialProof,
    // JSON data
    val rawVCData: String
)