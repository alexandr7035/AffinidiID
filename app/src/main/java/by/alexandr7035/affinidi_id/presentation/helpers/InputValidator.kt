package by.alexandr7035.affinidi_id.presentation.helpers

interface InputValidator {
    fun validatePassword(password: String): InputValidationResult
}