package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCredentialsListUseCase @Inject constructor(
    private val storedCredentialsRepository: StoredCredentialsRepository
) {

    suspend fun execute(): Flow<CredentialsListResModel> {
        return storedCredentialsRepository.getAllCredentials()
    }
}