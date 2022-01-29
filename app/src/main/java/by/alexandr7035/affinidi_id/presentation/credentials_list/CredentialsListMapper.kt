package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel

interface CredentialsListMapper {
    fun map(domainCredentials: CredentialsListResModel): CredentialListUiModel
}