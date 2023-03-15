package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import com.toxicbakery.logging.Arbor
import com.toxicbakery.logging.Seedling
import javax.inject.Inject

class CheckIfAuthorizedUseCase @Inject constructor(
    private val appSettings: AppSettings,
    private val signInWithRefreshTokenUseCase: SignInWithRefreshTokenUseCase
) {
    suspend fun execute(): GenericRes<Unit> {
        Arbor.sow(Seedling())

        val auth = appSettings.getAuthCredentials()
        Arbor.tag("TAG_AUTH").d("try to auth with ...${appSettings.getAuthCredentials().accessToken.takeLast(5)} access token")

        // Access token is set and not expired
        val tokenExpired = auth.accessTokenObtained + ACCESS_TOKEN_VALIDITY_MS <= System.currentTimeMillis()

        // Token not expired
        return if (auth.accessToken != "" && !tokenExpired) {
            Arbor.tag("TAG_AUTH").d("token not expired")
            GenericRes.Success(Unit)
        }

        // Token is set but expired, try to refresh
        else if (auth.accessToken != "") {
            Arbor.tag("TAG_AUTH").d("token expired, try to refresh")
            // Return the response of refreshing token
            signInWithRefreshTokenUseCase.execute()
        }

        // No access token
        else {
            Arbor.tag("TAG_AUTH").d("no access token, unauthorized")
            GenericRes.Fail(ErrorType.NOT_AUTHORIZED)
        }
    }

    companion object {
        private const val ACCESS_TOKEN_VALIDITY_MS = 3600_000
    }
}