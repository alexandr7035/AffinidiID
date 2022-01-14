package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.alexandr7035.affinidi_id.domain.repository.RegistrationRepository
import javax.inject.Inject

class RegisterWithEmailUseCase @Inject constructor(private val registrationRepository: RegistrationRepository) {
    suspend fun execute(signUpRequestModel: SignUpRequestModel): SignUpResponseModel {
        return registrationRepository.signUp(signUpRequestModel)
    }
}