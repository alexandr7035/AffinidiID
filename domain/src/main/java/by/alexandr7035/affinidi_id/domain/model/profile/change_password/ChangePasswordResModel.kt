package by.alexandr7035.affinidi_id.domain.model.profile.change_password

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ChangePasswordResModel {
    object Success : ChangePasswordResModel()

    class Fail(val errorType: ErrorType): ChangePasswordResModel()
}