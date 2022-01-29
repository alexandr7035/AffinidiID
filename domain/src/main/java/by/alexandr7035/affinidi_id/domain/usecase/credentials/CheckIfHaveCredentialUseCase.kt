package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import javax.inject.Inject

class CheckIfHaveCredentialUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository) {
    suspend fun execute(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel {
        return credentialsRepository.checkIfHaveCredentialInCache(checkIfHaveVcReqModel)
    }
}