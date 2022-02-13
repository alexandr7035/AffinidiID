package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.StoredCredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class ShareCredentialUseCase @Inject constructor(private val storedCredentialsRepository: StoredCredentialsRepository, private val getAuthStateUseCase: GetAuthStateUseCase) {
    suspend fun execute(shareCredentialsReq: ShareCredentialReqModel): ShareCredentialResModel {
        val authState = getAuthStateUseCase.execute()
        return storedCredentialsRepository.shareCredential(shareCredentialsReq, authState)
    }
}