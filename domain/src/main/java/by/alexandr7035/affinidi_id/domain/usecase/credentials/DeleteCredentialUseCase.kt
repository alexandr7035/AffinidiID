package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import javax.inject.Inject

class DeleteCredentialUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository) {
    suspend fun execute(deleteVcReqModel: DeleteVcReqModel): DeleteVcResModel {
        return DeleteVcResModel.Success()
    }
}