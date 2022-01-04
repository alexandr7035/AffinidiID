package by.alexandr7035.affinidi_id.data.model.change_username

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class ChangeUserNameModel {
    class Success(val newUserName: String): ChangeUserNameModel()

    class Fail(val errorType: ErrorType): ChangeUserNameModel()
}