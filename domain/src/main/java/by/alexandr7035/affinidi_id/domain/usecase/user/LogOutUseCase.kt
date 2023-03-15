package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import by.alexandr7035.affinidi_id.domain.usecase.applock.SetAppLockedWithBiometricsUseCase
import javax.inject.Inject

class LogOutUseCase @Inject constructor(
    private val loginRepository: LoginRepository,
    // FIXME move to data layer
//    private val clearProfileUseCase: ClearProfileUseCase,
    private val setAppLockedWithBiometricsUseCase: SetAppLockedWithBiometricsUseCase
) {
    suspend fun execute(): LogOutModel {
        // FIXME login repository
//        val accessToken = checkIfAuthorizedUseCase.execute().accessToken
        val result = loginRepository.logOut("")

        setAppLockedWithBiometricsUseCase.execute(locked = false)

        // TODO refactoring
        // Logout in any case
        when (result) {
            is LogOutModel.Success -> {
                // Clear data
//                clearProfileUseCase.execute()
            }
            is LogOutModel.Fail -> {
                if (result.errorType == ErrorType.AUTH_SESSION_EXPIRED) {
                    // Means authorization token already not actual
                    // Just clear token and login and return success logout
//                    loginRepository.saveAccessToken(null)
                    // TODO clear data
                    return LogOutModel.Success
                }
            }
        }

        return result
    }
}