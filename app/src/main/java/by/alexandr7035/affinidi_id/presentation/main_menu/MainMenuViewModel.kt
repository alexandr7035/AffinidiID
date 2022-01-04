package by.alexandr7035.affinidi_id.presentation.main_menu

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.data.ProfileRepository
import by.alexandr7035.affinidi_id.data.model.profile.UserProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainMenuViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfileModel>()

    fun init() {
        userProfileLiveData.value = repository.getProfile()
    }

    fun getProfileImageUrl(userDid: String): String {
        return repository.getProfileImageUrl(userDid)
    }

}