package by.alexandr7035.affinidi_id.presentation

import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.data.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val authRepository: AuthRepository): ViewModel() {
    fun checkIfAuthorized(): Boolean {
        return authRepository.checkIfAuthorized()
    }
}