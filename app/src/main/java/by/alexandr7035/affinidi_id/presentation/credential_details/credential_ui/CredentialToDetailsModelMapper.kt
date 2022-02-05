package by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDetailsUiModel

interface CredentialToDetailsModelMapper {
    fun map(credential: Credential): CredentialDetailsUiModel.Success
}