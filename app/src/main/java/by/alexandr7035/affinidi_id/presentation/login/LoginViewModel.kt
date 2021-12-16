package by.alexandr7035.affinidi_id.presentation.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.data.AuthRepository
import by.alexandr7035.affinidi_id.data.model.SignInModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: AuthRepository): ViewModel() {
    val signInLiveData = MutableLiveData<SignInModel>()

    fun signIn(userName: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.signIn(userName, password)

            withContext(Dispatchers.Main) {
                signInLiveData.value = result
            }
        }
    }
}