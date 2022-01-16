package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class BuildUnsignedVcObjectUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository, private val authStateUseCase: GetAuthStateUseCase) {
    suspend fun execute(issueCredentialReqModel: IssueCredentialReqModel): IssueCredentialResModel {
       return credentialsRepository.issueCredential(issueCredentialReqModel, authStateUseCase.execute())
    }
}