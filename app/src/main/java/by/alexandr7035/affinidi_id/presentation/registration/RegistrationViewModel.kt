package by.alexandr7035.affinidi_id.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.domain.model.profile.SaveProfileModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.ConfirmSignUpResponseModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpRequestModel
import by.alexandr7035.affinidi_id.domain.model.signup.SignUpResponseModel
import by.alexandr7035.affinidi_id.domain.usecase.user.ConfirmRegisterWithEmailUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.RegisterWithEmailUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.SaveProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val registerWithEmailUseCase: RegisterWithEmailUseCase,
    private val confirmRegisterWithEmailUseCase: ConfirmRegisterWithEmailUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val inputValidationHelper: InputValidationHelper
): ViewModel() {

    val signUpLiveData = SingleLiveEvent<SignUpResponseModel>()
    val signUpConfirmationLiveData = SingleLiveEvent<ConfirmSignUpResponseModel>()

    fun signUp(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = registerWithEmailUseCase.execute(SignUpRequestModel(userName, password))

            withContext(Dispatchers.Main) {
                signUpLiveData.value = result
            }
        }
    }

    fun confirmSignUp(confirmationToken: String, confirmationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = confirmRegisterWithEmailUseCase.execute(ConfirmSignUpRequestModel(confirmationToken, confirmationCode))

            withContext(Dispatchers.Main) {
                signUpConfirmationLiveData.value = result
            }
        }
    }

    fun saveProfile(userName: String, userDid: String) {
        saveProfileUseCase.execute(SaveProfileModel(userName, userDid))
    }

    fun validateUserName(userName: String): InputValidationResult {
        return inputValidationHelper.validateUserName(userName)
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }

    fun validateConfirmationCode(code: String): InputValidationResult {
        return inputValidationHelper.validateConfirmationCode(code)
    }
}