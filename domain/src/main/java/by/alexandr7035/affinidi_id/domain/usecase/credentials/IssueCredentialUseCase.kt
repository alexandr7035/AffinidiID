package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.IssueCredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class IssueCredentialUseCase @Inject constructor(
    private val issueCredentialsRepository: IssueCredentialsRepository,
    private val authStateUseCase: GetAuthStateUseCase,
    private val checkIfHaveCredentialUseCase: CheckIfHaveCredentialUseCase
) {
    suspend fun execute(issueCredentialReqModel: IssueCredentialReqModel): IssueCredentialResModel {

        // Check if have credential with requested context url in cache
        // Do not issue a new if have
        val checkForCredential = checkIfHaveCredentialUseCase.execute(
            checkIfHaveVcReqModel = CheckIfHaveVcReqModel(
                vcContextUrl = issueCredentialReqModel.issuingCredentialData.jsonLdContextUrl,
                // Allow issuing if previous VC is expired
                includeExpired = false
            )
        )

        return if (checkForCredential.haveCredential) {
            IssueCredentialResModel.Fail(ErrorType.ALREADY_HAVE_CREDENTIAL)
        } else {
            // Issue a new one
            issueCredentialsRepository.issueCredential(issueCredentialReqModel, authStateUseCase.execute())
        }
    }
}