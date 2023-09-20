package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.alexandr7035.affinidi_id.domain.repository.RegistrationRepository
import kotlinx.coroutines.delay

// TODO use generic res
class RegistrationRepositoryMock: RegistrationRepository {
    override suspend fun signUp(signUpRequestModel: SignUpRequestModel): SignUpResponseModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return SignUpResponseModel.Success(
            confirmSignUpToken = "token",
            userName = MockConstants.MOCK_LOGIN
        )
    }

    override suspend fun confirmSignUp(confirmSignUpRequestModel: ConfirmSignUpRequestModel): ConfirmSignUpResponseModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return ConfirmSignUpResponseModel.Success(
            accessToken = "token",
            userDid = MockConstants.MOCK_DID
        )
    }
}