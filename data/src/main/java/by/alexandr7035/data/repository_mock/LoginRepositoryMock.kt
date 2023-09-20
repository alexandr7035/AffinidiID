package by.alexandr7035.data.repository_mock

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.model.login.AuthCredentials
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import kotlinx.coroutines.delay

class LoginRepositoryMock(
    private val appSettings: AppSettings
): LoginRepository {
    override suspend fun signIn(userName: String, password: String): GenericRes<Unit> {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        return if (userName == MockConstants.MOCK_LOGIN && password == MockConstants.MOCK_PASSWORD) {
            appSettings.setAuthCredentials(
                AuthCredentials(
                    userDid = MockConstants.MOCK_DID,
                    userEmail = MockConstants.MOCK_LOGIN,
                    accessToken = "access_token_mock",
                    refreshToken = "refresh_token_mock",
                    accessTokenObtained = System.currentTimeMillis()
                )
            )

            GenericRes.Success(Unit)
        } else {
            GenericRes.Fail(ErrorType.WRONG_USERNAME_OR_PASSWORD)
        }
    }

    override suspend fun signInWithRefreshToken(): GenericRes<Unit> {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        appSettings.updateAuthCredentials(
            accessToken = "access_token_mock",
            accessTokenRefreshedDate = System.currentTimeMillis()
        )

        return GenericRes.Success(Unit)
    }

    override suspend fun logOut(): LogOutModel {
        delay(MockConstants.MOCK_REQ_DELAY_MILLS)

        appSettings.clearSettings()
        return LogOutModel.Success
    }
}