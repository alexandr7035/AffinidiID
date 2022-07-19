package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card

import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusUi

data class CredentialCardUi(
    val id: String,
    val holderDid: String,
    val issuerDid: String,
    val issuanceDateText: String,
    val credentialStatusUi: CredentialStatusUi,
    val credentialTypeText: String,
    val credentialExpirationText: String,
)