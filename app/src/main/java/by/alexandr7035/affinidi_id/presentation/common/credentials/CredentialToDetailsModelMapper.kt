package by.alexandr7035.affinidi_id.presentation.common.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential

interface CredentialToDetailsModelMapper {
    fun map(credential: Credential): CredentialDetailsUiModel.Success
}