package by.alexandr7035.affinidi_id.presentation.common.validation

interface InputValidationHelper {
    fun validateUserName(userName: String): InputValidationResult

    fun validatePassword(password: String): InputValidationResult

    fun validateConfirmationCode(confirmationCode: String): InputValidationResult
}