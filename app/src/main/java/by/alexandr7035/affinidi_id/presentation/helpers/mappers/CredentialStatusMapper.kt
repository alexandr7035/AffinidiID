package by.alexandr7035.affinidi_id.presentation.helpers.mappers

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.CredentialStatusUi

interface CredentialStatusMapper {
    fun map(credentialStatus: CredentialStatus): CredentialStatusUi
}