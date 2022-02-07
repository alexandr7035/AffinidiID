package by.alexandr7035.affinidi_id.presentation.common.credentials.verification

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class VerificationResultToUiMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider
) : VerificationResultToUiMapper {
    override fun map(verifyVcResModel: VerifyVcResModel): VerificationModelUi = when (verifyVcResModel) {

        is VerifyVcResModel.Success -> {
            val uiVerificationModel = when (verifyVcResModel.isValid) {
                true -> {
                    VerificationModelUi.Success(
                        validationResultSnackBarMode = SnackBarMode.Positive,
                        messageText = resourceProvider.getString(R.string.vc_is_valid),
                    )
                }
                false -> {
                    VerificationModelUi.Success(
                        validationResultSnackBarMode = SnackBarMode.Negative,
                        messageText = resourceProvider.getString(R.string.vc_is_not_valid),
                    )
                }
            }

            uiVerificationModel
        }

        is VerifyVcResModel.Fail -> {
            VerificationModelUi.Fail(errorType = verifyVcResModel.errorType)
        }
    }
}
