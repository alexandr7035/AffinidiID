package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.domain.repository.ChangeProfileRepository
import kotlinx.coroutines.delay

class ChangeProfileRepositoryMock : ChangeProfileRepository {
    override suspend fun changePassword(changePasswordReqModel: ChangePasswordReqModel): ChangePasswordResModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return if (changePasswordReqModel.oldPassword == MockConstants.MOCK_PASSWORD) {
            ChangePasswordResModel.Success
        } else {
            ChangePasswordResModel.Fail(ErrorType.WRONG_CURRENT_PASSWORD)
        }
    }
}