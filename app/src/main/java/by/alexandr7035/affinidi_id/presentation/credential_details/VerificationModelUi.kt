package by.alexandr7035.affinidi_id.presentation.credential_details

import by.alexandr7035.affinidi_id.domain.core.ErrorType


sealed class VerificationModelUi {
    data class Success(
        val isValid: Boolean,
        val messageText: String
    ) : VerificationModelUi()

    data class Fail(val errorType: ErrorType) : VerificationModelUi()
}