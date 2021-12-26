package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.ResetPasswordRepository
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializePasswordResetModel
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializeResetPasswordRequest
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(private val apiService: ApiService): ResetPasswordRepository{
    override suspend fun initializePasswordReset(userName: String): InitializePasswordResetModel {
        try {
            val res = apiService.initializePasswordReset(InitializeResetPasswordRequest(userName))

            return if (res.isSuccessful) {
                InitializePasswordResetModel.Success(userName)
            } else {
                when (res.code()) {
                    400 -> InitializePasswordResetModel.Fail(ErrorType.WRONG_CONFIRMATION_CODE)
                    404 -> InitializePasswordResetModel.Fail(ErrorType.USER_DOES_NOT_EXIST)
                    else -> InitializePasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return InitializePasswordResetModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return InitializePasswordResetModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun confirmResetPassword() {
        TODO("Not yet implemented")
    }
}