package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.domain.repository.ChangeProfileRepository
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.model.network.profile.ChangePasswordRequest
import javax.inject.Inject

class ChangeProfileRepositoryImpl @Inject constructor(private val apiService: UserApiService): ChangeProfileRepository {
    override suspend fun changePassword(changePasswordReqModel: ChangePasswordReqModel, authStateModel: AuthStateModel): ChangePasswordResModel {
        try {
            val res = apiService.changePassword(
                accessToken = authStateModel.accessToken ?: "",
                body = ChangePasswordRequest(
                    oldPassword = changePasswordReqModel.oldPassword,
                    newPassword = changePasswordReqModel.newPassword,
                )
            )

            return if (res.isSuccessful) {
                ChangePasswordResModel.Success
            } else {
                when (res.code()) {
                    400 -> {
                        ChangePasswordResModel.Fail(ErrorType.WRONG_CURRENT_PASSWORD)
                    }
                    // Unknown fail code
                    else -> {
                        ChangePasswordResModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ChangePasswordResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return ChangePasswordResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}