package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import kotlinx.coroutines.delay

class VerificationRepositoryMock: VerificationRepository {
    override suspend fun obtainCredentialFromQrCode(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): ObtainVcFromQrCodeResModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return ObtainVcFromQrCodeResModel.Success(
            credential = MockConstants.CREDENTIALS[0]
        )
    }

    override suspend fun verifyCredential(verifyVcReqModel: VerifyVcReqModel): VerifyVcResModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)
        return VerifyVcResModel.Success(isValid = true)
    }
}