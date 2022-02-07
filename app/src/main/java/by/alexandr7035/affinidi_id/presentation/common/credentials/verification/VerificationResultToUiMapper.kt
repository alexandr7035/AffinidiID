package by.alexandr7035.affinidi_id.presentation.common.credentials.verification

import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel

interface VerificationResultToUiMapper {
    fun map(verifyVcResModel: VerifyVcResModel): VerificationModelUi
}