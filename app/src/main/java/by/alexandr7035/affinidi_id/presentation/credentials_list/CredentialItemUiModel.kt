package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusUi

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val expirationDate: String,
    val credentialStatus: CredentialStatusUi,
)