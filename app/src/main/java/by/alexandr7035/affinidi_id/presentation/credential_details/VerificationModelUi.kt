package by.alexandr7035.affinidi_id.presentation.credential_details

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode


sealed class VerificationModelUi {
    data class Success(
        val messageText: String,
        val validationResultSnackBarMode: SnackBarMode
    ) : VerificationModelUi()

    data class Fail(val errorType: ErrorType) : VerificationModelUi()
}