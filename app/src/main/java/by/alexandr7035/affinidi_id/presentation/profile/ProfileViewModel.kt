package by.alexandr7035.affinidi_id.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.data.ProfileRepository
import by.alexandr7035.affinidi_id.data.model.UserProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfile>()

    fun init() {
        userProfileLiveData.value = repository.getProfile()
    }

    fun getProfileImageUrl(userDid: String): String {
        return repository.getProfileImageUrl(userDid)
    }
}