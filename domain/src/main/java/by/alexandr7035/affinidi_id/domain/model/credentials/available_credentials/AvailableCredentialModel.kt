package by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials

data class AvailableCredentialModel(
    val typeName: String,
    val description: String,
    val issuer: String,
    val vcType: AvailableVcType
)