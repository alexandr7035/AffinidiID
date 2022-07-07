package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_metadata

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi

interface CredentialMetadataToFieldsMapper {
    fun map(credential: Credential): List<CredentialFieldUi>
}