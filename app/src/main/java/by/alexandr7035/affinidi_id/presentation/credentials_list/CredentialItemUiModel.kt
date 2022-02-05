package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.presentation.common.CredentialStatusUi

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val isUnknownType: Boolean,
    val expirationDate: String,
    val credentialStatus: CredentialStatusUi,
)