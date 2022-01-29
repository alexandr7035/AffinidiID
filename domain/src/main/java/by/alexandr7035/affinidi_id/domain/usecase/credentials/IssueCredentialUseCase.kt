package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class IssueCredentialUseCase @Inject constructor(
    private val credentialsRepository: CredentialsRepository,
    private val authStateUseCase: GetAuthStateUseCase,
    private val checkIfHaveCredentialUseCase: CheckIfHaveCredentialUseCase
) {
    suspend fun execute(issueCredentialReqModel: IssueCredentialReqModel): IssueCredentialResModel {

        return if (checkIfHaveCredentialUseCase.execute()) {
            // If already have credential
            IssueCredentialResModel.Fail(ErrorType.ALREADY_HAVE_CREDENTIAL)
        } else {
            // Issue a new one
            credentialsRepository.issueCredential(issueCredentialReqModel, authStateUseCase.execute())
        }
    }
}