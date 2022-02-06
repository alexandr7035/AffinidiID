package by.alexandr7035.affinidi_id.presentation.credential_details

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialStatusUi

sealed class CredentialDetailsUiModel {
    data class Success(
        val credentialId: String,
        val credentialType: String,
        val rawVcDataPrettyFormatted: String,
        val credentialSubjectItems: List<CredentialDataItem>,
        val metadataItems: List<CredentialDataItem>,
        val proofItems: List<CredentialDataItem>,
        val credentialStatus: CredentialStatusUi
    ): CredentialDetailsUiModel()

    data class Fail(val errorType: ErrorType): CredentialDetailsUiModel()

    object Loading: CredentialDetailsUiModel()
}