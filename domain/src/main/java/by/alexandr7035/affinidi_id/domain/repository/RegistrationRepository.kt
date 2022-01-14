package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel

interface RegistrationRepository {
    suspend fun signUp(signUpRequestModel: SignUpRequestModel): SignUpResponseModel

    suspend fun confirmSignUp(confirmSignUpRequestModel: ConfirmSignUpRequestModel): ConfirmSignUpResponseModel
}