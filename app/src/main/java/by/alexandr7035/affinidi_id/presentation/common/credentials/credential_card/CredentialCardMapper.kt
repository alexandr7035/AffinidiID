package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential

interface CredentialCardMapper {
    fun map(credential: Credential): CredentialCardUi
}