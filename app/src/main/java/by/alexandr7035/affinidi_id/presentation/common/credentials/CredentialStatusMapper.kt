package by.alexandr7035.affinidi_id.presentation.common.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus

interface CredentialStatusMapper {
    fun map(credentialStatus: CredentialStatus): CredentialStatusUi
}