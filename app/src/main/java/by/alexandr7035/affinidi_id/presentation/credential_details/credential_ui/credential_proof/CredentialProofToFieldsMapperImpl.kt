package by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_proof

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataItem
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import javax.inject.Inject

class CredentialProofToFieldsMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider) : CredentialProofToFieldsMapper {
    override fun map(credential: Credential): List<CredentialDataItem> {
        return  listOf(
            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.created),
                value = credential.credentialProof.creationDate,
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.type),
                value = credential.credentialProof.type,
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.proof_purpose),
                value = credential.credentialProof.proofPurpose,
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.verification_method),
                value = credential.credentialProof.verificationMethod,
            ),

            CredentialDataItem.Field(
                name = resourceProvider.getString(R.string.jws),
                value = credential.credentialProof.jws,
            )
        )
    }
}