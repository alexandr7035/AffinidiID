package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_metadata

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi
import javax.inject.Inject

class CredentialMetadataToFieldsMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider):
    CredentialMetadataToFieldsMapper {
    override fun map(credential: Credential): List<CredentialFieldUi> {

        // Cut DID after ";" (delete initial state, etc.)
        val formattedIssuerDid = credential.issuerDid.split(";").first()

        return listOf(
            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.credential_id),
                value = credential.id
            ),
            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.issuer),
                value = formattedIssuerDid
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.holder),
                value = credential.holderDid
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.issuance_date),
                value = credential.issuanceDate.getStringDateFromLong(DATE_FORMAT)
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.expiration_date),
                value = credential.expirationDate?.getStringDateFromLong(DATE_FORMAT)
                    ?: resourceProvider.getString(R.string.no_expiration)
            )
        )
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:ss"
    }
}