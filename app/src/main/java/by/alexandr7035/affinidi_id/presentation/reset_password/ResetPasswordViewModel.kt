package by.alexandr7035.affinidi_id.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.common.validation.InputValidationResult
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.ConfirmPasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetRequestModel
import by.alexandr7035.affinidi_id.domain.model.reset_password.InitializePasswordResetResponseModel
import by.alexandr7035.affinidi_id.domain.usecase.user.ConfirmPasswordResetUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.InitializePasswordResetUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val initializePasswordResetUseCase: InitializePasswordResetUseCase,
    private val confirmPasswordResetUseCase: ConfirmPasswordResetUseCase,
    private val inputValidationHelper: InputValidationHelper
): ViewModel() {
    val initializePasswordResetLiveData = SingleLiveEvent<InitializePasswordResetResponseModel>()
    val confirmPasswordResetLiveData = SingleLiveEvent<ConfirmPasswordResetResponseModel>()

    fun initializePasswordReset(username: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = initializePasswordResetUseCase.execute(InitializePasswordResetRequestModel(username))

            withContext(Dispatchers.Main) {
                initializePasswordResetLiveData.value = result
            }
        }
    }

    fun confirmPasswordReset(username: String, newPassword: String, confirmationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = confirmPasswordResetUseCase.execute(ConfirmPasswordResetRequestModel(
                userName = username,
                newPassword = newPassword,
                confirmationCode = confirmationCode
            ))

            withContext(Dispatchers.Main) {
                confirmPasswordResetLiveData.value = result
            }
        }
    }

    fun validateUserName(username: String): InputValidationResult {
        return inputValidationHelper.validateUserName(username)
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }

    fun validateConfirmationCode(code: String): InputValidationResult {
        return inputValidationHelper.validateConfirmationCode(code)
    }
}