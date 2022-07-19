package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialsListUseCase
import by.alexandr7035.affinidi_id.presentation.credentials_list.filters.CredentialFilters
import by.alexandr7035.affinidi_id.presentation.credentials_list.model.CredentialListUiModel
import by.alexandr7035.affinidi_id.presentation.credentials_list.model.CredentialsListMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialsListViewModel @Inject constructor(
    private val getCredentialsListUseCase: GetCredentialsListUseCase,
    private val mapper: CredentialsListMapper
) : ViewModel() {

    private val credentialsLiveData = MutableLiveData<CredentialListUiModel>()

    fun load(credentialFilters: CredentialFilters) {
        viewModelScope.launch(Dispatchers.IO) {
            getCredentialsListUseCase.execute().collect { res ->
                // Map credentials to ui. Apply filters
                val credentialListUiModel = mapper.map(res, credentialFilters)

                withContext(Dispatchers.Main) {
                    credentialsLiveData.value = credentialListUiModel
                }
            }
        }
    }

    fun getCredentialsLiveData(): LiveData<CredentialListUiModel> {
        return credentialsLiveData
    }

}