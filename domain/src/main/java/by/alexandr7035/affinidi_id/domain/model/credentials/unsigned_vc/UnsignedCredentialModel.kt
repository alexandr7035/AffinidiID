package by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc

import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialStatus

data class UnsignedCredentialModel(
    val credentialType: String,
    val id: String,
    val holderDid: String,
    val issuerDid: String,
    val issuanceDate: Long,
    val expirationDate: Long?,
    val credentialStatus: CredentialStatus
)