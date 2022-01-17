package by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types

data class AvailableCredentialTypeModel(
    val typeName: String,
    val description: String,
    val issuer: String,
    val vcType: AvailableVcType
)