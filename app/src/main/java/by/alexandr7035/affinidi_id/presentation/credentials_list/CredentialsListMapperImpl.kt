package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class CredentialsListMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val credentialStatusMapper: CredentialStatusMapper,
    private val errorTypeMapper: ErrorTypeMapper
) : CredentialsListMapper {

    override fun map(domainCredentials: CredentialsListResModel): CredentialListUiModel {
        return when (domainCredentials) {
            is CredentialsListResModel.Success -> {

                val uiCredentials: List<CredentialItemUiModel> = domainCredentials.credentials.map {

                    val textExpirationDate = if (it.expirationDate != null) {

                        when (it.credentialStatus) {
                            CredentialStatus.ACTIVE -> {
                                resourceProvider.getString(
                                    R.string.credential_active_until_template,
                                    it.expirationDate!!.getStringDateFromLong(CARD_EXPIRATION_DATE_FORMAT)
                                )
                            }

                            CredentialStatus.EXPIRED -> {
                                resourceProvider.getString(
                                    R.string.credential_expired_at_template,
                                    it.expirationDate!!.getStringDateFromLong(CARD_EXPIRATION_DATE_FORMAT)
                                )
                            }
                        }

                    } else {
                        resourceProvider.getString(
                            R.string.no_expiration
                        )
                    }

                    // Map domain fields to UI
                    val credentialStatus = credentialStatusMapper.map(it.credentialStatus)

                    CredentialItemUiModel(
                        id = it.id,
                        expirationDate = textExpirationDate,
                        credentialTypeString = it.vcType,
                        credentialStatus = credentialStatus,
                    )
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

    companion object {
        private const val CARD_EXPIRATION_DATE_FORMAT = "dd/MMM/YYYY"
    }
}