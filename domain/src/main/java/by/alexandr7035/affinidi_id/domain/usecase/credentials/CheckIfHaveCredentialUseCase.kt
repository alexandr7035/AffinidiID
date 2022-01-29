package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel

class CheckIfHaveCredentialUseCase {
    fun execute(): CheckIfHaveVcResModel {
        return CheckIfHaveVcResModel(haveCredential = true)
    }
}