package by.alexandr7035.affinidi_id.data.model.change_username

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class ConfirmChangeUserNameModel {
    class Success(val newUserName: String): ConfirmChangeUserNameModel()

    class Fail(val errorType: ErrorType): ConfirmChangeUserNameModel()
}