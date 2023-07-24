package by.alexandr7035.affinidi_id.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.alexandr7035.affinidi_id.domain.usecase.user.SignInWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val inputValidationHelper: InputValidationHelper
) : ViewModel() {
    val signInLiveData = SingleLiveEvent<GenericRes<Unit>>()

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = signInWithEmailUseCase.execute(userName, password)

            withContext(Dispatchers.Main) {
                signInLiveData.value = result
            }
        }
    }

    fun validateUserName(userName: String): InputValidationResult {
        return inputValidationHelper.validateUserName(userName)
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }
}