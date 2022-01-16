package by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc

import by.alexandr7035.affinidi_id.domain.core.ErrorType


abstract class BuildUnsignedVcResModel {
    class Success(val unsignedCredential: UnsignedCredentialModel): BuildUnsignedVcResModel()

    class Fail(val errorType: ErrorType): BuildUnsignedVcResModel()
}