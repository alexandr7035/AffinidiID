package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import javax.inject.Inject

class ObtainCredentialWithQrCodeUseCase @Inject constructor(private val verificationRepository: VerificationRepository) {
    suspend fun execute(obtainVcFromQrCodeReq: ObtainVcFromQrCodeReqModel): Credential {
        return verificationRepository.obtainCredentialFromQrCode(obtainVcFromQrCodeReq)
    }
}