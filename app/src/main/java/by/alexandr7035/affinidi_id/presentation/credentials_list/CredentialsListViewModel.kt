package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialsListUseCase
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialsListViewModel @Inject constructor(
    private val getCredentialsListUseCase: GetCredentialsListUseCase,
    private val resourceProvider: ResourceProvider,
    private val mapper: CredentialsListMapper
) : ViewModel() {

    private val credentialsLiveData = MutableLiveData<CredentialListUiModel>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            getCredentialsListUseCase.execute().collect { res ->
                val credentialListUiModel = mapper.map(res)

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