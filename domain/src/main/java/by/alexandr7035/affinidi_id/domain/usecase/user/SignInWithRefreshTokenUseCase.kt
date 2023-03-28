package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository

class SignInWithRefreshTokenUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(): GenericRes<Unit> {
        return loginRepository.signInWithRefreshToken()
    }
}
