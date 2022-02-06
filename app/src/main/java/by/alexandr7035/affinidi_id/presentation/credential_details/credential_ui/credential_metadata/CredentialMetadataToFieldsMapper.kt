package by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_metadata

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataItem

interface CredentialMetadataToFieldsMapper {
    fun map(credential: Credential): List<CredentialDataItem>
}