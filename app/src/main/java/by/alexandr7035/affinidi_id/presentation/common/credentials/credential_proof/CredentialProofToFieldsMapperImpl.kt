package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi
import javax.inject.Inject

class CredentialProofToFieldsMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider) :
    CredentialProofToFieldsMapper {
    override fun map(credentialProof: CredentialProof): List<CredentialFieldUi> {

        val formattedCreationDate = credentialProof.creationDate.getStringDateFromLong(PROOF_DATE_FORMAT)

        return  listOf(
            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.created),
                value = formattedCreationDate,
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.type),
                value = credentialProof.type,
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.proof_purpose),
                value = credentialProof.proofPurpose,
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.verification_method),
                value = credentialProof.verificationMethod,
            ),

            CredentialFieldUi.Field(
                name = resourceProvider.getString(R.string.jws),
                value = credentialProof.jws,
            )
        )
    }

    companion object {
        private const val PROOF_DATE_FORMAT = "dd MMM YYYY HH:mm:ss"
    }
}