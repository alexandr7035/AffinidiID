package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialStatus

data class CredentialItemUiModel(
    val id: String,
    val credentialType: String,
    val expirationDate: String,
    val credentialStatus: String,
    val statusMarkColor: Int
)