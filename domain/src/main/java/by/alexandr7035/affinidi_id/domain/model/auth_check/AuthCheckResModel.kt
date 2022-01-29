package by.alexandr7035.affinidi_id.domain.model.auth_check

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class AuthCheckResModel{
    object Success : AuthCheckResModel()

    class Fail(val errorType: ErrorType): AuthCheckResModel()
}
