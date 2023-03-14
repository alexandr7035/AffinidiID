package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import javax.inject.Inject

class CheckIfAuthorizedUseCase @Inject constructor(
    private val appSettings: AppSettings,
    private val signInWithRefreshTokenUseCase: SignInWithRefreshTokenUseCase
) {
    // TODO auth check and refreshing token
    suspend fun execute(): GenericRes<Unit> {
        val auth = appSettings.getAuthCredentials()

        // Access token is set and not expired
        return if (auth.accessToken != "" && auth.accessTokenObtained + ACCESS_TOKEN_VALIDITY_MS >= System.currentTimeMillis()) {
            GenericRes.Success(Unit)
        }

        // Token is set but expired, try to refresh
        else if (auth.accessToken != "") {
            signInWithRefreshTokenUseCase.execute(auth.accessToken)
        }

        // No access token
        else {
            // TODO diff UNAUTHORIZED and AUTH ERROR if needed
            GenericRes.Fail(ErrorType.AUTHORIZATION_ERROR)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_VALIDITY_MS = 3600_000
    }
}