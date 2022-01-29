package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.repository.AuthCheckRepository
import javax.inject.Inject

// Ass accessToken expires fastly we need to check for auth on app started
// Send simple request, catch and handle result, e.g. in MainActivity
class AuthCheckUseCase @Inject constructor(
    private val authCheckRepository: AuthCheckRepository,
    private val getAuthStateUseCase: GetAuthStateUseCase
) {
    suspend fun execute(): AuthCheckResModel {
        val authState = getAuthStateUseCase.execute()
        return authCheckRepository.makeCheckRequest(authState)
    }
}