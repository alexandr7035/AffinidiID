package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class DeleteCredentialUseCase @Inject constructor(
    private val credentialsRepository: CredentialsRepository,
    private val getAuthStateUseCase: GetAuthStateUseCase
) {
    suspend fun execute(deleteVcReqModel: DeleteVcReqModel): DeleteVcResModel {
        val authState = getAuthStateUseCase.execute()
        return credentialsRepository.deleteCredential(deleteVcReqModel, authState)
    }
}