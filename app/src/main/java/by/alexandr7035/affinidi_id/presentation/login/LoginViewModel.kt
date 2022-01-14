package by.alexandr7035.affinidi_id.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.domain.model.login.SignInModel
import by.alexandr7035.affinidi_id.domain.model.profile.SaveProfileModel
import by.alexandr7035.affinidi_id.domain.usecase.SaveProfileUseCase
import by.alexandr7035.affinidi_id.domain.usecase.SignInWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInWithEmailUseCase: SignInWithEmailUseCase,
    private val saveProfileUseCase: SaveProfileUseCase,
    private val inputValidationHelper: InputValidationHelper
) : ViewModel() {
    val signInLiveData = SingleLiveEvent<SignInModel>()

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = signInWithEmailUseCase.execute(userName, password)

            withContext(Dispatchers.Main) {
                signInLiveData.value = result
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
}