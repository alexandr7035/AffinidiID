package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel

interface VerificationRepository {
    suspend fun obtainCredentialFromQrCode(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): ObtainVcFromQrCodeResModel
}