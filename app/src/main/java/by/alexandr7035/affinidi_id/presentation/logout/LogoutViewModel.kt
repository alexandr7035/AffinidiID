package by.alexandr7035.affinidi_id.presentation.logout

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.login.LogOutModel
import by.alexandr7035.affinidi_id.domain.usecase.user.LogOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(private val logOutUseCase: LogOutUseCase): ViewModel() {
    var logOutLiveData = MutableLiveData<LogOutModel>()

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = logOutUseCase.execute()

            withContext(Dispatchers.Main) {
                logOutLiveData.value = res
            }
        }
    }
}