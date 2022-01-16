package by.alexandr7035.affinidi_id.domain.model.credentials

import by.alexandr7035.affinidi_id.domain.core.ErrorType


abstract class CredentialsListResModel {
    class Success(val credentials: List<Credential>): CredentialsListResModel()

    class Fail(errorType: ErrorType): CredentialsListResModel()
}
