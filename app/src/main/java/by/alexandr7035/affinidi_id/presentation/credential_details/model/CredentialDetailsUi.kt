package by.alexandr7035.affinidi_id.presentation.credential_details.model

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDataItem
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusUi

sealed class CredentialDetailsUi {
    data class Success(
        val credentialCardUi: CredentialCardUi,
        val credentialSubjectItems: List<CredentialDataItem>,
        val metadataItems: List<CredentialDataItem>,
        val proofItems: List<CredentialDataItem>,
        val credentialStatus: CredentialStatusUi,
        val rawVcDataPrettyFormatted: String
    ) : CredentialDetailsUi()

    data class Fail(val errorType: ErrorType) : CredentialDetailsUi()

    object Loading : CredentialDetailsUi()
}