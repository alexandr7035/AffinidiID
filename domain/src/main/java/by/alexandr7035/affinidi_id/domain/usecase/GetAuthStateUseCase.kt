package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import javax.inject.Inject

class GetAuthStateUseCase @Inject constructor(private val loginRepository: LoginRepository){
    fun execute(): AuthStateModel {
        val accessToken = loginRepository.getAccessToken()

        return if (accessToken == null) {
            AuthStateModel(isAuthorized = false, accessToken = null)
        }
        else {
            AuthStateModel(isAuthorized = true, accessToken = accessToken)
        }
    }
}