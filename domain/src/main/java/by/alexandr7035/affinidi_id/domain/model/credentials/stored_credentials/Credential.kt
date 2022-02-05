package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData

data class Credential(
    val vcType: VcType,
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