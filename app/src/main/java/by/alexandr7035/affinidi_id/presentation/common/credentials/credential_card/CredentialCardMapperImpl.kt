package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class CredentialCardMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider
) : CredentialCardMapper {
    override fun map(credential: Credential): CredentialCardUi {

        val credentialStatusText = if (credential.expirationDate != null) {

            when (credential.credentialStatus) {
                CredentialStatus.ACTIVE -> {
                    resourceProvider.getString(
                        R.string.credential_active_until_template,
                        credential.expirationDate!!.getStringDateFromLong(CARD_EXPIRATION_DATE_FORMAT)
                    )
                }

                CredentialStatus.EXPIRED -> {
                    resourceProvider.getString(
                        R.string.credential_expired_at_template,
                        credential.expirationDate!!.getStringDateFromLong(CARD_EXPIRATION_DATE_FORMAT)
                    )
                }
            }

        } else {
            resourceProvider.getString(
                R.string.no_expiration
            )
        }

        return CredentialCardUi(
            id = credential.id,
            credentialStatus = credential.credentialStatus,
            credentialStatusText = credentialStatusText,
            credentialTypeText = credential.vcType,
        )
    }

    companion object {
        private const val CARD_EXPIRATION_DATE_FORMAT = "dd/MMM/YYYY"
    }
}