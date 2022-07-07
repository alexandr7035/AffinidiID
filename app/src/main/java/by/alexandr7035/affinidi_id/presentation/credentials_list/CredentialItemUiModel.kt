package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val expirationDate: String,
    val credentialStatus: CredentialStatus,
)