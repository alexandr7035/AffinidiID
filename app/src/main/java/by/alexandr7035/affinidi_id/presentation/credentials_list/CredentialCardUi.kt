package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

data class CredentialCardUi(
    val id: String,
    val credentialStatus: CredentialStatus,
    val credentialTypeText: String,
    val credentialStatusText: String,
)