package by.alexandr7035.affinidi_id.data

import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordModel
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameModel
import by.alexandr7035.affinidi_id.data.model.change_username.ConfirmChangeUserNameModel

interface ChangeProfileRepository {
    suspend fun changePassword(oldPassword: String, newPassword: String): ChangePasswordModel

    suspend fun changeUserName(newUserName: String): ChangeUserNameModel

    suspend fun confirmChangeUserName(newUserName: String, confirmationCode: String): ConfirmChangeUserNameModel

    fun saveUserName(userName: String)
}