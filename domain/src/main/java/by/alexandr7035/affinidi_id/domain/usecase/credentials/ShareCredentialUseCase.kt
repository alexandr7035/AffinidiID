package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import javax.inject.Inject

class ShareCredentialUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository) {
    suspend fun execute(shareCredentialsReq: ShareCredentialReqModel): ShareCredentialResModel {
        return credentialsRepository.shareCredential(shareCredentialsReq)
    }
}