package by.alexandr7035.affinidi_id.data.helpers.validation

import android.util.Patterns

class InputValidationHelperImpl: InputValidationHelper {
    override fun validateUserName(userName: String): InputValidationResult {
        return when {
            userName.isEmpty() -> InputValidationResult.EMPTY_FIELD

            ! Patterns.EMAIL_ADDRESS.matcher(userName).matches() -> InputValidationResult.WRONG_FORMAT

            else -> InputValidationResult.NO_ERRORS
        }
    }

    override fun validatePassword(password: String): InputValidationResult {

        return when {
            password.isEmpty() -> {
                InputValidationResult.EMPTY_FIELD
            }

            // TODO validate here

            else -> InputValidationResult.NO_ERRORS
        }
    }

    override fun validateConfirmationCode(confirmationCode: String): InputValidationResult {
        return when {
            confirmationCode.isEmpty() -> {
                InputValidationResult.EMPTY_FIELD
            }

            else -> InputValidationResult.NO_ERRORS
        }
    }
}