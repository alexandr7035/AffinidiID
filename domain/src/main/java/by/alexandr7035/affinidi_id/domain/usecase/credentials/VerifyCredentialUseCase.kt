package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class VerifyCredentialUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository, private val getAuthStateUseCase: GetAuthStateUseCase) {
    fun execute(verifyVcReq: VerifyVcReqModel): VerifyVcResModel {
        // TODO FIXME
        return VerifyVcResModel(isValid = true)
    }
}