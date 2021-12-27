package by.alexandr7035.affinidi_id.presentation.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.data.LoginRepository
import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val repository: LoginRepository): ViewModel() {
    var logOutLiveData = MutableLiveData<LogOutModel>()

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.logOut()

            withContext(Dispatchers.Main) {
                logOutLiveData.value = res
            }
        }
    }
}