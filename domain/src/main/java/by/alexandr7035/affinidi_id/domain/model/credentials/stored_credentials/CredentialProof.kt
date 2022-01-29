package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

data class CredentialProof(
    val type: String,
    val creationDate: String,
    val verificationMethod: String,
    val proofPurpose: String,
    val jws: String
)