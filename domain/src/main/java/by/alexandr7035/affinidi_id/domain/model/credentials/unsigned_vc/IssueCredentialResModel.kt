package by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc

import by.alexandr7035.affinidi_id.domain.core.ErrorType


abstract class IssueCredentialResModel {
    class Success(): IssueCredentialResModel()

    class Fail(val errorType: ErrorType): IssueCredentialResModel()
}