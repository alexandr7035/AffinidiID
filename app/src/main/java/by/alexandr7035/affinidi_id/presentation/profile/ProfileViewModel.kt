package by.alexandr7035.affinidi_id.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.domain.model.profile.UserProfile
import by.alexandr7035.affinidi_id.domain.usecase.user.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfile>()

    fun init() {
        userProfileLiveData.value = getProfileUseCase.execute()
    }
}