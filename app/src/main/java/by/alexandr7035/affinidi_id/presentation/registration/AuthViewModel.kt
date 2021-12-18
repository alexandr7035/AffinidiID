package by.alexandr7035.affinidi_id.presentation.registration

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpConfirmationModel
import by.alexandr7035.affinidi_id.data.model.sign_up.SignUpModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {

    val signUpLiveData = MutableLiveData<SignUpModel>()
    val signUpConfirmationLiveData = MutableLiveData<SignUpConfirmationModel>()

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

}