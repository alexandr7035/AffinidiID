package by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential

sealed class ObtainVcFromQrCodeResModel {
    data class Success(val credential: Credential): ObtainVcFromQrCodeResModel()

    data class Fail(val errorType: ErrorType): ObtainVcFromQrCodeResModel()
}