package by.alexandr7035.affinidi_id.presentation

import androidx.lifecycle.ViewModel
import by.alexandr7035.affinidi_id.domain.usecase.GetAuthStateUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val getAuthStateUseCase: GetAuthStateUseCase): ViewModel() {
    fun checkIfAuthorized(): Boolean {
        return getAuthStateUseCase.execute().isAuthorized
    }
}