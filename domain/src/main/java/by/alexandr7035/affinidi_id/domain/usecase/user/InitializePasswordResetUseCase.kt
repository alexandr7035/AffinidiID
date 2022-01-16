package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.repository.ResetPasswordRepository
import javax.inject.Inject

class InitializePasswordResetUseCase @Inject constructor(private val resetPasswordRepository: ResetPasswordRepository) {
    suspend fun execute(initializePasswordResetRequestModel: InitializePasswordResetRequestModel): InitializePasswordResetResponseModel {
        return resetPasswordRepository.initializePasswordReset(initializePasswordResetRequestModel)
    }
}