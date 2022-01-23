package by.alexandr7035.affinidi_id.presentation.credential_details

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class CredentialDetailsUiModel {
    data class Success(val detailsItems: List<CredentialDataItem>): CredentialDetailsUiModel()

    data class Fail(val errorType: ErrorType): CredentialDetailsUiModel()

    object Loading: CredentialDetailsUiModel()
}