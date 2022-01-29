package by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc

import by.alexandr7035.affinidi_id.domain.core.ErrorType


sealed class IssueCredentialResModel {
    object Success : IssueCredentialResModel()

    class Fail(val errorType: ErrorType): IssueCredentialResModel()
}