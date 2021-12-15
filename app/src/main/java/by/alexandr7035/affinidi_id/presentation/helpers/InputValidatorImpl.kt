package by.alexandr7035.affinidi_id.presentation.helpers

class InputValidatorImpl: InputValidator {
    override fun validatePassword(password: String): InputValidationResult {
        // TODO format
        return if (password.isBlank()) {
            InputValidationResult.PASSWORD_IS_EMPTY
        } else {
            InputValidationResult.NO_ERRORS
        }
    }
}