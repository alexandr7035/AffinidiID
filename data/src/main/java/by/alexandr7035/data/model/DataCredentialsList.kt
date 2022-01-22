package by.alexandr7035.data.model

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential

sealed class DataCredentialsList {
    class Success(val signedCredentials: List<SignedCredential>): DataCredentialsList()

    // Domain errorType here
    class Fail(val errorType: ErrorType): DataCredentialsList()
}