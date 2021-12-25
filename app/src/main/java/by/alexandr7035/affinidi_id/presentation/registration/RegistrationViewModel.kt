package by.alexandr7035.affinidi_id.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val inputValidationHelper: InputValidationHelper
): ViewModel() {

    val signUpLiveData = SingleLiveEvent<SignUpModel>()
    val signUpConfirmationLiveData = SingleLiveEvent<SignUpConfirmationModel>()

    fun signUp(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.signUp(userName, password)

            withContext(Dispatchers.Main) {
                signUpLiveData.value = result
            }
        }
    }

    fun confirmSignUp(confirmationToken: String, confirmationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.confirmSignUp(confirmationToken, confirmationCode)

            withContext(Dispatchers.Main) {
                signUpConfirmationLiveData.value = result
            }
        }
    }

    fun saveUserName(userName: String) {
        repository.saveUserName(userName)
    }

    fun validateUserName(userName: String): InputValidationResult {
        return inputValidationHelper.validateUserName(userName)
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }

}