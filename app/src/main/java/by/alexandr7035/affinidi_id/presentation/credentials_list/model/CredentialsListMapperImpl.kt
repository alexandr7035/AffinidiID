package by.alexandr7035.affinidi_id.presentation.credentials_list.model

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.filters.CredentialFilters
import javax.inject.Inject

class CredentialsListMapperImpl @Inject constructor(
    private val credentialCardMapper: CredentialCardMapper,
    private val errorTypeMapper: ErrorTypeMapper
) : CredentialsListMapper {

    override fun map(domainCredentials: CredentialsListResModel, credentialFilters: CredentialFilters): CredentialListUiModel {
        return when (domainCredentials) {
            is CredentialsListResModel.Success -> {

                val uiCredentialsUnfiltered: List<CredentialCardUi> = domainCredentials.credentials.map {
                    credentialCardMapper.map(it)
                }

                val uiCredentialsFiltered = when (credentialFilters) {
                    CredentialFilters.All -> {
                        uiCredentialsUnfiltered
                    }

                    CredentialFilters.Active -> {
                        uiCredentialsUnfiltered.filter {
                            it.credentialStatusUi.domainStatus == CredentialStatus.ACTIVE
                        }
                    }

                    CredentialFilters.Expired -> {
                        uiCredentialsUnfiltered.filter {
                            it.credentialStatusUi.domainStatus == CredentialStatus.EXPIRED
                        }
                    }
                }

                if (uiCredentialsFiltered.isEmpty()) {
                    CredentialListUiModel.NoCredentials
                } else {
                    CredentialListUiModel.Success(uiCredentialsFiltered)
                }
            }

            is CredentialsListResModel.Fail -> {
                CredentialListUiModel.Fail(errorUi = errorTypeMapper.map(errorType = domainCredentials.errorType))
            }

            is CredentialsListResModel.Loading -> {
                CredentialListUiModel.Loading
            }
        }
    }
}