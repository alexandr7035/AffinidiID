package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import javax.inject.Inject

class VerifyCredentialUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository) {
    suspend fun execute(verifyVcReq: VerifyVcReqModel): VerifyVcResModel {
        return credentialsRepository.verifyCredential(verifyVcReq)
    }
}