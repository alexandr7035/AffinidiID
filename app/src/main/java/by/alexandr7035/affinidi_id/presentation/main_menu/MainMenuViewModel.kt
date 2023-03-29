package by.alexandr7035.affinidi_id.presentation.main_menu

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.usecase.applock.CheckAppLockedWithBiometricsUseCase
import by.alexandr7035.affinidi_id.domain.usecase.applock.SetAppLockedWithBiometricsUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.GetProfileUseCase
import by.alexandr7035.affinidi_id.presentation.common.biometrics.BiometricsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val checkAppLockedWithBiometricsUseCase: CheckAppLockedWithBiometricsUseCase,
    private val setAppLockedWithBiometricsUseCase: SetAppLockedWithBiometricsUseCase
) : ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfile>()

    private val _biometricsAvailableStatusLiveData = MutableLiveData<Int>()
    val biometricsAvailableStatusLiveData = _biometricsAvailableStatusLiveData

    fun init() {
        userProfileLiveData.value = getProfileUseCase.execute()
    }

    fun checkBiometricAvailability(context: Context) {
        val res = BiometricsHelper.checkIfBiometricsAvailable(context)
        biometricsAvailableStatusLiveData.value = res
    }

    fun checkAppLockedWithBiometrics(): Boolean {
        return checkAppLockedWithBiometricsUseCase.execute()
    }

    fun setAppLockedWithBiometrics(locked: Boolean) {
        setAppLockedWithBiometricsUseCase.execute(locked)
    }
}