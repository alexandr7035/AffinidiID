package by.alexandr7035.affinidi_id.data.model.change_password

import by.alexandr7035.affinidi_id.core.ErrorType

abstract class ChangePasswordModel {
    class Success(): ChangePasswordModel()

    class Fail(val errorType: ErrorType): ChangePasswordModel()
}
