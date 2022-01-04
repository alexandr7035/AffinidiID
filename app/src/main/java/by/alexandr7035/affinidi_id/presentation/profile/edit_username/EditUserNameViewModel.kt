package by.alexandr7035.affinidi_id.presentation.profile.edit_username

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.data.ChangeProfileRepository
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import by.alexandr7035.affinidi_id.data.model.change_username.ChangeUserNameModel
import by.alexandr7035.affinidi_id.data.model.change_username.ConfirmChangeUserNameModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class EditUserNameViewModel @Inject constructor(
    private val repository: ChangeProfileRepository,
    private val inputValidationHelper: InputValidationHelper
) : ViewModel() {

    val editUserNameLiveData = SingleLiveEvent<ChangeUserNameModel>()
    val confirmEditUserNameLiveData = SingleLiveEvent<ConfirmChangeUserNameModel>()

    fun changeUserName(newUserName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.changeUserName(newUserName)

            withContext(Dispatchers.Main) {
                editUserNameLiveData.value = res
            }
        }
    }

    fun saveUserNameToCache(userName: String) {
        repository.saveUserName(userName)
    }

    fun confirmChangeUserName(newUserName: String, confirmationCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.confirmChangeUserName(newUserName, confirmationCode)

            withContext(Dispatchers.Main) {
                confirmEditUserNameLiveData.value = res
            }
        }
    }

    fun validateUserName(userName: String): InputValidationResult {
        return inputValidationHelper.validateUserName(userName)
    }

    fun validateConfirmationCode(code: String): InputValidationResult {
        return inputValidationHelper.validateConfirmationCode(code)
    }
}