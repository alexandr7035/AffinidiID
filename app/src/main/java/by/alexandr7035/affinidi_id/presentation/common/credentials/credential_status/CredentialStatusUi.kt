package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

data class CredentialStatusUi(
    val domainStatus: CredentialStatus,
    val statusText: String,
    val statusColor: Int
)