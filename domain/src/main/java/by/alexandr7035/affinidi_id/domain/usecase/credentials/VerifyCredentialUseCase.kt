package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.repository.VerificationRepository
import javax.inject.Inject

class VerifyCredentialUseCase @Inject constructor(private val verificationRepository: VerificationRepository) {
    suspend fun execute(verifyVcReq: VerifyVcReqModel): VerifyVcResModel {
        return verificationRepository.verifyCredential(verifyVcReq)
    }
}