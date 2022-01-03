package by.alexandr7035.affinidi_id.data.implementation

import by.alexandr7035.affinidi_id.core.AppError
import by.alexandr7035.affinidi_id.core.ErrorType
import by.alexandr7035.affinidi_id.data.ApiService
import by.alexandr7035.affinidi_id.data.AuthDataStorage
import by.alexandr7035.affinidi_id.data.ChangeProfileRepository
import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordModel
import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordRequest
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameModel
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameRequest
import javax.inject.Inject

class ChangeProfileRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val authDataStorage: AuthDataStorage
) : ChangeProfileRepository {

    override suspend fun changePassword(oldPassword: String, newPassword: String): ChangePasswordModel {
        try {
            val res = apiService.changePassword(
                accessToken = authDataStorage.getAccessToken() ?: "",
                body = ChangePasswordRequest(
                    oldPassword = oldPassword,
                    newPassword = newPassword,
                )
            )

            return if (res.isSuccessful) {
                ChangePasswordModel.Success()
            } else {
                when (res.code()) {
                    400 -> {
                        ChangePasswordModel.Fail(ErrorType.WRONG_CURRENT_PASSWORD)
                    }
                    // Unknown fail code
                    else -> {
                        ChangePasswordModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ChangePasswordModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return ChangePasswordModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }


    override suspend fun changeUserName(newUserName: String): ChangeUserNameModel {
        try {
            val res = apiService.changeUserName(
                accessToken = authDataStorage.getAccessToken() ?: "",
                body = ChangeUserNameRequest(
                    newUserName = newUserName
                )
            )

            return if (res.isSuccessful) {
                ChangeUserNameModel.Success(newUserName = newUserName)
            } else {
                when (res.code()) {
                    409 -> {
                        ChangeUserNameModel.Fail(ErrorType.USER_ALREADY_EXISTS)
                    }
                    // Unknown fail code
                    else -> {
                        ChangeUserNameModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return ChangeUserNameModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            return ChangeUserNameModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }
}