package by.alexandr7035.affinidi_id.domain.core

enum class ErrorType {
    FAILED_CONNECTION,
    AUTHORIZATION_ERROR,
    USER_ALREADY_EXISTS,
    USER_DOES_NOT_EXIST,
    // Use at sign in
    WRONG_USERNAME_OR_PASSWORD,
    // Use at change password
    WRONG_CURRENT_PASSWORD,
    WRONG_CONFIRMATION_CODE,
    CONFIRMATION_CODE_DIALOG_DISMISSED,
    UNKNOWN_ERROR
}