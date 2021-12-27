package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.reset_password.ConfirmPasswordResetModel
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializePasswordResetModel

interface ResetPasswordRepository {
    suspend fun initializePasswordReset(userName: String): InitializePasswordResetModel

    suspend fun confirmResetPassword(userName: String, newPassword: String, confirmationCode: String): ConfirmPasswordResetModel
}