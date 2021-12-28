package by.alexandr7035.affinidi_id.presentation.profile.edit_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.data.ChangeProfileRepository
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.change_password.ChangePasswordModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(
    private val repository: ChangeProfileRepository,
    private val inputValidationHelper: InputValidationHelper
): ViewModel() {
    val changePasswordLiveData = SingleLiveEvent<ChangePasswordModel>()

    fun changePassword(oldPassword: String, newPassword: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.changePassword(oldPassword, newPassword)

            withContext(Dispatchers.Main) {
                changePasswordLiveData.value = res
            }
        }
    }

    fun validatePassword(password: String): InputValidationResult {
        return inputValidationHelper.validatePassword(password)
    }
}