package by.alexandr7035.affinidi_id.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.model.auth_check.AuthCheckResModel
import by.alexandr7035.affinidi_id.domain.usecase.user.AuthCheckUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val authCheckUseCase: AuthCheckUseCase
) : ViewModel() {

    private val authCheckLiveData = SingleLiveEvent<AuthCheckResModel>()

    fun checkIfPreviouslyAuthorized(): Boolean {
        return getAuthStateUseCase.execute().isAuthorized
    }

    fun startAuthCheck() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = authCheckUseCase.execute()

            withContext(Dispatchers.Main) {
//                authCheckLiveData.value = res
                // FIXME debug
                authCheckLiveData.value = AuthCheckResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
            }
        }
    }

    fun getAuthCheckLiveData(): LiveData<AuthCheckResModel> {
        return authCheckLiveData
    }
}