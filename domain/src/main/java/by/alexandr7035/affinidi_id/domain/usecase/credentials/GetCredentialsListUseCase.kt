package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCredentialsListUseCase @Inject constructor(
    private val storedCredentialsRepository: StoredCredentialsRepository,
    private val authStateUseCase: GetAuthStateUseCase
) {

    suspend fun execute(): Flow<CredentialsListResModel> {
        return storedCredentialsRepository.getAllCredentials(authState = authStateUseCase.execute())
    }
}