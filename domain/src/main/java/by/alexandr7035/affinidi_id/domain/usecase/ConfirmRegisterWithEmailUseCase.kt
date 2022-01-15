package by.alexandr7035.affinidi_id.domain.usecase

import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.repository.RegistrationRepository
import javax.inject.Inject

class ConfirmRegisterWithEmailUseCase @Inject constructor(private val registrationRepository: RegistrationRepository) {
    suspend fun execute(confirmSignUpRequestModel: ConfirmSignUpRequestModel): ConfirmSignUpResponseModel {
        // todo save auth data here
        return registrationRepository.confirmSignUp(confirmSignUpRequestModel)
    }
}