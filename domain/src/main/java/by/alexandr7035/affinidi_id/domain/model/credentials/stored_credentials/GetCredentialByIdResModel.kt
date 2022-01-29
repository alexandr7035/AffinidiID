package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class GetCredentialByIdResModel{
    data class Success(val credential: Credential): GetCredentialByIdResModel()

    data class Fail(val errorType: ErrorType): GetCredentialByIdResModel()

    object Loading : GetCredentialByIdResModel()
}