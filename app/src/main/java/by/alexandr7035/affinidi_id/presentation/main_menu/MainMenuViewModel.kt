package by.alexandr7035.affinidi_id.presentation.main_menu

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.usecase.user.GetProfileUseCase
import by.alexandr7035.affinidi_id.presentation.common.biometrics.BiometricsHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(private val getProfileUseCase: GetProfileUseCase) : ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfile>()

    private val _biometricsAvailableStatusLiveData = MutableLiveData<Int>()
    val biometricsAvailableStatusLiveData = _biometricsAvailableStatusLiveData

    private val biometricsHelper = BiometricsHelper()

    fun init() {
        userProfileLiveData.value = getProfileUseCase.execute()
    }

    fun checkBiometricAvailability(context: Context) {
        val res = biometricsHelper.checkIfBiometricsAvailable(context)
        biometricsAvailableStatusLiveData.value = res
    }
}