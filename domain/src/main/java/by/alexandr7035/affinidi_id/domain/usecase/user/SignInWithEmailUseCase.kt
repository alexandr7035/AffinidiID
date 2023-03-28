package by.alexandr7035.affinidi_id.domain.usecase.user

import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.repository.LoginRepository
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(private val loginRepository: LoginRepository) {
    suspend fun execute(userName: String, password: String): GenericRes<Unit> {
        return loginRepository.signIn(userName, password)
    }
}