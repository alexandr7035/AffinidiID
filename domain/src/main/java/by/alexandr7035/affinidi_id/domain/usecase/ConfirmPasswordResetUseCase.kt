package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.repository.ResetPasswordRepository
import javax.inject.Inject

class ConfirmPasswordResetUseCase @Inject constructor(private val resetPasswordRepository: ResetPasswordRepository) {
    suspend fun execute(confirmPasswordResetRequestModel: ConfirmPasswordResetRequestModel): ConfirmPasswordResetResponseModel {
        return resetPasswordRepository.confirmResetPassword(confirmPasswordResetRequestModel)
    }
}