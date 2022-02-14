package by.alexandr7035.affinidi_id.domain.model.credentials.share

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ShareCredentialResModel {
    data class Success(val base64QrCode: String): ShareCredentialResModel()

    data class Fail(val errorType: ErrorType): ShareCredentialResModel()
}


