package by.alexandr7035.affinidi_id.presentation.profile.edit_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.presentation.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordReqModel
import by.alexandr7035.affinidi_id.domain.model.profile.change_password.ChangePasswordResModel
import by.alexandr7035.affinidi_id.domain.usecase.user.ChangePasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val changePasswordUseCase: ChangePasswordUseCase,
    private val inputValidationHelper: InputValidationHelper
): ViewModel() {
    val changePasswordLiveData = SingleLiveEvent<ChangePasswordResModel>()

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = changePasswordUseCase.execute(ChangePasswordReqModel(
                oldPassword = oldPassword,
                newPassword = newPassword
            ))

            withContext(Dispatchers.Main) {
                changePasswordLiveData.value = res
            }
        }
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }
}