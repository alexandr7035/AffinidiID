package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel

interface VerificationRepository {
    suspend fun obtainCredentialFromQrCode(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): ObtainVcFromQrCodeResModel

    suspend fun verifyCredential(verifyVcReqModel: VerifyVcReqModel): VerifyVcResModel
}