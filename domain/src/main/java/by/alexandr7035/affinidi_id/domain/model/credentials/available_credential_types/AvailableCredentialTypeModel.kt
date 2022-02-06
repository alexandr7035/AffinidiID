package by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssuingCredentialType

data class AvailableCredentialTypeModel(
    val typeName: String,
    val description: String,
    val issuer: String,
    val issuingCredentialType: IssuingCredentialType
)