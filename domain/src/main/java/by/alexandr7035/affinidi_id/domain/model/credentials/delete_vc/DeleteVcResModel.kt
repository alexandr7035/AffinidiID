package by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc

import by.alexandr7035.affinidi_id.domain.core.ErrorType

abstract class DeleteVcResModel {
    class Success : DeleteVcResModel()

    class Fail(val errorType: ErrorType): DeleteVcResModel()
}