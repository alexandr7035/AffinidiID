package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel

interface ChangeProfileRepository {
    suspend fun changePassword(changePasswordReqModel: ChangePasswordReqModel, authStateModel: AuthStateModel): ChangePasswordResModel
}