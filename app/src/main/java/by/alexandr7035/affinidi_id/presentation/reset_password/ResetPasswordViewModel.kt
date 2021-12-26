package by.alexandr7035.affinidi_id.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.data.ResetPasswordRepository
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.reset_password.InitializePasswordResetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val resetPasswordRepository: ResetPasswordRepository, private val inputValidationHelper: InputValidationHelper): ViewModel() {
    val initializePasswordResetLiveData = SingleLiveEvent<InitializePasswordResetModel>()

    fun initializePasswordReset(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = resetPasswordRepository.initializePasswordReset(username)

            withContext(Dispatchers.Main) {
                initializePasswordResetLiveData.value = result
            }
        }
    }

    fun validateUserName(username: String): InputValidationResult {
        return inputValidationHelper.validateUserName(username)
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }
}