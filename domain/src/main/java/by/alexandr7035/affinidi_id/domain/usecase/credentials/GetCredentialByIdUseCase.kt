package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCredentialByIdUseCase @Inject constructor(
    private val credentialsRepository: CredentialsRepository,
    private val getAuthStateUseCase: GetAuthStateUseCase
) {
    suspend fun execute(getCredentialByIdReq: GetCredentialByIdReqModel): Flow<GetCredentialByIdResModel> {
        val authState = getAuthStateUseCase.execute()
        return credentialsRepository.getCredentialById(getCredentialByIdReq, authState)
    }
}