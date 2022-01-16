package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialsListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialsListViewModel @Inject constructor(private val getCredentialsListUseCase: GetCredentialsListUseCase): ViewModel() {

    private val credentialsLiveData = MutableLiveData<List<CredentialItemUiModel>>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCredentialsListUseCase.execute()

            // FIXME
            val domainCreds = if (res is CredentialsListResModel.Success) {
                res.credentials
            }
            else {
                emptyList()
            }

            val listCreds: List<CredentialItemUiModel> = domainCreds.map {

                val textExpirationDate = it.expirationDate?.getStringDateFromLong("dd.MM.YYYY HH:MM") ?: "-"

                CredentialItemUiModel(
                    expirationDate = "Expires: ${textExpirationDate}",
                    credentialType = it.credentialType
                )
            }

            withContext(Dispatchers.Main) {
                credentialsLiveData.value = listCreds
            }
        }
    }

    fun getCredentialsLiveData(): LiveData<List<CredentialItemUiModel>> {
        return credentialsLiveData
    }
}