package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import javax.inject.Inject

class CredentialsListMapperImpl @Inject constructor(
    private val credentialCardMapper: CredentialCardMapper,
    private val errorTypeMapper: ErrorTypeMapper
) : CredentialsListMapper {

    override fun map(domainCredentials: CredentialsListResModel): CredentialListUiModel {
        return when (domainCredentials) {
            is CredentialsListResModel.Success -> {

                val uiCredentials: List<CredentialCardUi> = domainCredentials.credentials.map {
                    credentialCardMapper.map(it)
                }

                if (uiCredentials.isEmpty()) {
                    CredentialListUiModel.NoCredentials
                } else {
                    CredentialListUiModel.Success(uiCredentials)
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