package by.alexandr7035.affinidi_id.presentation.credentials_list.model

import by.alexandr7035.affinidi_id.presentation.common.credentials.credential_card.CredentialCardUi
import by.alexandr7035.affinidi_id.presentation.common.errors.DetailedErrorUi

sealed class CredentialListUiModel {
    class Success(val credentials: List<CredentialCardUi>): CredentialListUiModel()

    class Fail(val errorUi: DetailedErrorUi): CredentialListUiModel()

    object NoCredentials : CredentialListUiModel()

    object Loading : CredentialListUiModel()
}