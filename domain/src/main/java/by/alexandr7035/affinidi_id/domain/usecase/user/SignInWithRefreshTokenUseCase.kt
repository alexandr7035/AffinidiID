package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import kotlinx.coroutines.delay

class SignInWithRefreshTokenUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(accessToken: String): GenericRes.Success<Unit> {
        // TODO
        delay(3000)
        return GenericRes.Success(Unit)
    }
}
