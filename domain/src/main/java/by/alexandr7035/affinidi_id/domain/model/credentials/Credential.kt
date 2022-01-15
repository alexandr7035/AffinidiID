package by.alexandr7035.affinidi_id.domain.model.credentials

data class Credential(
    val credentialType: String,
    val id: String,
    val holderDid: String,
    val issuerDid: String,
    val issuanceDate: Long,
    val expirationDate: Long?,
)