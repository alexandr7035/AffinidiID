package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject

data class Credential(
    val vcType: VcType,
    val credentialSubject: CredentialSubject,
    val id: String,
    val holderDid: String,
    val issuerDid: String,
    val issuanceDate: Long,
    val expirationDate: Long?,
    val credentialStatus: CredentialStatus,
    val credentialProof: CredentialProof
)