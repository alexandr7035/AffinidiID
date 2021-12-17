package by.alexandr7035.affinidi_id.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.data.ProfileRepository
import by.alexandr7035.affinidi_id.data.model.log_out.LogOutModel
import by.alexandr7035.affinidi_id.data.model.profile.UserProfileModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository): ViewModel() {

    val userProfileLiveData = MutableLiveData<UserProfileModel>()
    var logOutLiveData = MutableLiveData<LogOutModel>()

    fun init() {
        userProfileLiveData.value = repository.getProfile()
    }

    fun getProfileImageUrl(userDid: String): String {
        return repository.getProfileImageUrl(userDid)
    }

    fun logOut() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = repository.logOut()

            withContext(Dispatchers.Main) {
                logOutLiveData.value = res
            }
        }
    }
}