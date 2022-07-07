package by.alexandr7035.affinidi_id.presentation.common.credentials

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status.CredentialStatusUi
import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialCardUi

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