package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.domain.repository.ChangeProfileRepository
import javax.inject.Inject

class ChangePasswordUseCase @Inject constructor(
    private val changeProfileRepository: ChangeProfileRepository,

)
{
    suspend fun execute(changePasswordReqModel: ChangePasswordReqModel): ChangePasswordResModel {
        return changeProfileRepository.changePassword(changePasswordReqModel)
    }
}