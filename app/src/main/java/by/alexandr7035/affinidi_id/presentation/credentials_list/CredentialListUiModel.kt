package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.core.ErrorType

abstract class CredentialListUiModel {
    class Success(val credentials: List<CredentialItemUiModel>): CredentialListUiModel()

    class Fail(val errorType: ErrorType): CredentialListUiModel()
}