package by.alexandr7035.affinidi_id.presentation.credential_details.model

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi

interface CredentialToDetailsModelMapper {
    fun map(credential: Credential): CredentialDetailsUi.Success
}