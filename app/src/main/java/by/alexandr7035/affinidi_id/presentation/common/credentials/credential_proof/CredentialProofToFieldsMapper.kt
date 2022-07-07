package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_proof

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi

interface CredentialProofToFieldsMapper {
    fun map(credentialProof: CredentialProof): List<CredentialFieldUi>
}