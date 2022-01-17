package by.alexandr7035.affinidi_id.presentation.issue_credential

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials.AvailableCredentialModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetAvailableVcTypesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IssueCredentialViewModel @Inject constructor(private val getAvailableVcTypesUseCase: GetAvailableVcTypesUseCase): ViewModel() {
    private val availableVCsLiveData = MutableLiveData<List<AvailableCredentialModel>>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getAvailableVcTypesUseCase.execute()

            withContext(Dispatchers.Main) {
                availableVCsLiveData.value = res
            }
        }
    }

    fun getAvailableVCsLiveData(): LiveData<List<AvailableCredentialModel>> {
        return availableVCsLiveData
    }
}