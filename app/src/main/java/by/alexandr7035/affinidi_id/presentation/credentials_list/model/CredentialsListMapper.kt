package by.alexandr7035.affinidi_id.presentation.credentials_list.model

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.credentials_list.filters.CredentialFilters

interface CredentialsListMapper {
    fun map(
        domainCredentials: CredentialsListResModel,
    ): CredentialListUiModel
}