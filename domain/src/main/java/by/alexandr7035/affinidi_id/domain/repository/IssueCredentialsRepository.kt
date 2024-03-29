package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel

interface IssueCredentialsRepository {
    suspend fun issueCredential(issueCredentialReqModel: IssueCredentialReqModel): IssueCredentialResModel
}