package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.domain.repository.ChangeProfileRepository
import by.alexandr7035.data.datasource.cloud.ApiCallHelper
import by.alexandr7035.data.datasource.cloud.ApiCallWrapper
import by.alexandr7035.data.datasource.cloud.api.UserApiService
import by.alexandr7035.data.model.network.profile.ChangePasswordRequest
import javax.inject.Inject

class ChangeProfileRepositoryImpl @Inject constructor(
    private val apiService: UserApiService,
    private val apiCallHelper: ApiCallHelper
) : ChangeProfileRepository {
    override suspend fun changePassword(changePasswordReqModel: ChangePasswordReqModel, authStateModel: AuthStateModel): ChangePasswordResModel {

        val res = apiCallHelper.executeCall {
            apiService.changePassword(
                accessToken = authStateModel.accessToken ?: "",
                body = ChangePasswordRequest(
                    oldPassword = changePasswordReqModel.oldPassword,
                    newPassword = changePasswordReqModel.newPassword,
                )
            )
        }

        return when (res) {
            is ApiCallWrapper.Success -> ChangePasswordResModel.Success
            is ApiCallWrapper.HttpError -> {
                when (res.resultCode) {
                    400 -> ChangePasswordResModel.Fail(ErrorType.WRONG_CURRENT_PASSWORD)
                    else -> ChangePasswordResModel.Fail(ErrorType.UNKNOWN_ERROR)
                }
            }
            is ApiCallWrapper.Fail -> ChangePasswordResModel.Fail(res.errorType)
        }
    }
}