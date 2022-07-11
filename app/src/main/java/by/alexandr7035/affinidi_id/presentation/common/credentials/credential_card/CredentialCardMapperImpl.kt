package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.core.extensions.getPrettifiedDid
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class CredentialCardMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val credentialStatusMapper: CredentialStatusMapper
) : CredentialCardMapper {
    override fun map(credential: Credential): CredentialCardUi {

        val credentialExpirationText = if (credential.expirationDate != null) {

            when (credential.credentialStatus) {
                CredentialStatus.ACTIVE -> {
                    resourceProvider.getString(
                        R.string.credential_active_until_template,
                        credential.expirationDate!!.getStringDateFromLong(CARD_DATE_FORMAT)
                    )
                }

                CredentialStatus.EXPIRED -> {
                    resourceProvider.getString(
                        R.string.credential_expired_at_template,
                        credential.expirationDate!!.getStringDateFromLong(CARD_DATE_FORMAT)
                    )
                }
            }

        } else {
            resourceProvider.getString(
                R.string.no_expiration
            )
        }

        val issuanceDate = resourceProvider.getString(
            R.string.credential_issued_on_template,
            credential.issuanceDate.getStringDateFromLong(CARD_DATE_FORMAT)
        )

        val formattedIssuerDid = credential.issuerDid.split(";").first()
        val prettifiedIssuerDid = formattedIssuerDid.getPrettifiedDid()

        val formattedHolderDid = credential.holderDid.split(";").first()
        val prettifiedHolderDid = formattedHolderDid.getPrettifiedDid()

        val credentialStatusUi = credentialStatusMapper.map(credential.credentialStatus)

        return CredentialCardUi(
            id = credential.id,
            issuerDid = prettifiedHolderDid,
            holderDid = prettifiedHolderDid,
            issuanceDateText = issuanceDate,
            credentialStatusUi = credentialStatusUi,
            credentialExpirationText = credentialExpirationText,
            credentialTypeText = credential.vcType,
        )
    }

    companion object {
        private const val CARD_DATE_FORMAT = "dd/MMM/YYYY"
    }
}