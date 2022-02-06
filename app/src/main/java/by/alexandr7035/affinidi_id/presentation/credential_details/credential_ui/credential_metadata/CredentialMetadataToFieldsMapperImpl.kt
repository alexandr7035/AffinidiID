package by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_metadata

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataItem
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class CredentialMetadataToFieldsMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider): CredentialMetadataToFieldsMapper {
    override fun map(credential: Credential): List<CredentialDataItem> {

        // Cut DID after ";" (delete initial state, etc.)
        val formattedIssuerDid = credential.issuerDid.split(";").first()

        return listOf(
            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.credential_id),
                value = credential.id
            ),
            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.issuer),
                value = formattedIssuerDid
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.holder),
                value = credential.holderDid
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.issuance_date),
                value = credential.issuanceDate.getStringDateFromLong(DATE_FORMAT)
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.expiration_date),
                value = credential.expirationDate?.getStringDateFromLong(DATE_FORMAT)
                    ?: resourceProvider.getString(R.string.no_expiration)
            )
        )
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:SS"
    }
}