package by.alexandr7035.affinidi_id.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.sign_in.SignInModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val inputValidationHelper: InputValidationHelper
) : ViewModel() {
    val signInLiveData = SingleLiveEvent<SignInModel>()

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.signIn(userName, password)

            withContext(Dispatchers.Main) {
                signInLiveData.value = result
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