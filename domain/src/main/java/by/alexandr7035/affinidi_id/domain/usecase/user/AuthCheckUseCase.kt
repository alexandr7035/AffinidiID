package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.repository.AuthCheckRepository
import javax.inject.Inject

// Ass accessToken expires fastly we need to check for auth on app started
// Send simple request, catch and handle result, e.g. in MainActivity
class AuthCheckUseCase @Inject constructor(
    private val authCheckRepository: AuthCheckRepository,
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val logOutUseCase: LogOutUseCase
) {
    suspend fun execute(): AuthCheckResModel {
        val authState = getAuthStateUseCase.execute()

        val authCheckResult = authCheckRepository.makeCheckRequest(authState)
//        val authCheckResult= AuthCheckResModel.Fail(ErrorType.AUTHORIZATION_ERROR)

        if (authCheckResult is AuthCheckResModel.Fail) {
            if (authCheckResult.errorType == ErrorType.AUTHORIZATION_ERROR) {
                // Clear token
                // As authCheckResult is Fail, logoutUseCase will logout successfully in any case
                logOutUseCase.execute()
            }
        }

        return authCheckResult
    }
}