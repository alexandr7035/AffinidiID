package by.alexandr7035.affinidi_id.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.core.GenericRes
import by.alexandr7035.affinidi_id.domain.usecase.applock.CheckAppLockedWithBiometricsUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.CheckIfAuthorizedUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.LogOutUseCase
import by.alexandr7035.affinidi_id.presentation.common.auth.AuthController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authCheckUseCase: CheckIfAuthorizedUseCase,
    private val checkAppLockedWithBiometricsUseCase: CheckAppLockedWithBiometricsUseCase,
    private val logOutUseCase: LogOutUseCase
) : ViewModel(), AuthController {

    private val authCheckLiveData = SingleLiveEvent<GenericRes<Unit>>()
    private val logoutLiveData = SingleLiveEvent<Unit>()

    override fun checkIfAuthorized() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = authCheckUseCase.execute()

            withContext(Dispatchers.Main) {
                authCheckLiveData.value = res
            }
        }
    }

    override fun getAuthCheckObservable(): LiveData<GenericRes<Unit>> {
        return authCheckLiveData
    }

    override fun checkAppLockedWithBiometrics(): Boolean {
        return checkAppLockedWithBiometricsUseCase.execute()
    }

    override fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = logOutUseCase.execute()

            withContext(Dispatchers.Main) {
                // TODO refactoring
                logoutLiveData.value = Unit
            }
        }
    }

    override fun getLogOutObservable(): LiveData<Unit> {
        return logoutLiveData
    }
}