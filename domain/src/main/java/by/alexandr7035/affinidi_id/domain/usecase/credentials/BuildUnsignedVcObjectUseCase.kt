package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.BuildUnsignedVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.BuildUnsignedVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import javax.inject.Inject

class BuildUnsignedVcObjectUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository) {
    suspend fun execute(buildUnsignedVcReqModel: BuildUnsignedVcReqModel): BuildUnsignedVcResModel {
       return credentialsRepository.buildUnsignedVCObject(buildUnsignedVcReqModel)
    }
}