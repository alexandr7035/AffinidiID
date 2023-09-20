package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.repository.IssueCredentialsRepository
import kotlinx.coroutines.delay

class IssueCredentialsRepositoryMock: IssueCredentialsRepository {
    override suspend fun issueCredential(issueCredentialReqModel: IssueCredentialReqModel): IssueCredentialResModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)
        return IssueCredentialResModel.Fail(ErrorType.ALREADY_HAVE_CREDENTIAL)
    }
}