package by.alexandr7035.data.model

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class DataGetCredentialById {
    data class Success(val credential: SignedCredential): DataGetCredentialById()

    data class Fail(val errorType: ErrorType): DataGetCredentialById()
}