package by.alexandr7035.affinidi_id.presentation.common.validation

import android.util.Patterns

class InputValidationHelperImpl(minPasswordLength: Int): InputValidationHelper {

    private val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{$minPasswordLength,}\$".toPattern()

    override fun validateUserName(userName: String): InputValidationResult {
        return when {
            userName.isBlank() -> InputValidationResult.EMPTY_FIELD
            ! Patterns.EMAIL_ADDRESS.matcher(userName).matches() -> InputValidationResult.WRONG_FORMAT
            else -> InputValidationResult.NO_ERRORS
        }
    }

    override fun validatePassword(password: String): InputValidationResult {
        return when {
            password.isBlank() -> InputValidationResult.EMPTY_FIELD
            ! passwordPattern.matcher(password).matches() -> InputValidationResult.WRONG_FORMAT
            else -> InputValidationResult.NO_ERRORS
        }
    }

    override fun validateConfirmationCode(confirmationCode: String): InputValidationResult {
        return when {
            confirmationCode.isBlank() -> InputValidationResult.EMPTY_FIELD
            else -> InputValidationResult.NO_ERRORS
        }
    }
}