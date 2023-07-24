package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import javax.inject.Inject

class ShareCredentialUseCase @Inject constructor(private val storedCredentialsRepository: StoredCredentialsRepository) {
    suspend fun execute(shareCredentialsReq: ShareCredentialReqModel): ShareCredentialResModel {
        return storedCredentialsRepository.shareCredential(shareCredentialsReq)
    }
}