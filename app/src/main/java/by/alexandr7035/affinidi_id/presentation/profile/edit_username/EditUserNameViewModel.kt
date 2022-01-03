package by.alexandr7035.affinidi_id.presentation.profile.edit_username

import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationHelper
import by.alexandr7035.affinidi_id.data.helpers.validation.InputValidationResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EditUserNameViewModel @Inject constructor(private val inputValidationHelper: InputValidationHelper) : ViewModel() {
    fun validateUserName(userName: String): InputValidationResult {
        return inputValidationHelper.validateUserName(userName)
    }
}