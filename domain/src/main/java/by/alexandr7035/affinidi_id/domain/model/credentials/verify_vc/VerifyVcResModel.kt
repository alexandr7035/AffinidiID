package by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class VerifyVcResModel {
    data class Success(val isValid: Boolean) : VerifyVcResModel()

    data class Fail(val errorType: ErrorType): VerifyVcResModel()
}