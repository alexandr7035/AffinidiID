package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val clearProfileUseCase: ClearProfileUseCase
) {
    suspend fun execute(): LogOutModel {
        val accessToken = getAuthStateUseCase.execute().accessToken
        val result = loginRepository.logOut(accessToken ?: "")

        when (result) {
            is LogOutModel.Success -> {
                clearProfileUseCase.execute()
            }
            is LogOutModel.Fail -> {
                if (result.errorType == ErrorType.AUTHORIZATION_ERROR) {
                    // Means authorization token already not actual
                    // Just clear token and login and return success logout
                    loginRepository.saveAccessToken(null)
                    return LogOutModel.Success
                }
            }
        }

        return result
    }
}