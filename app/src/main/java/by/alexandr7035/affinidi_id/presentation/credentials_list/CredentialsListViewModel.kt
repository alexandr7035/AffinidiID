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
class CredentialsListViewModel @Inject constructor(private val getCredentialsListUseCase: GetCredentialsListUseCase) : ViewModel() {

    private val credentialsLiveData = MutableLiveData<CredentialListUiModel>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCredentialsListUseCase.execute()

            val credentialListUiModel: CredentialListUiModel = when (res) {
                is CredentialsListResModel.Success -> {

                    // TODO mapper
                    val uiCredentials: List<CredentialItemUiModel> = res.credentials.map {

                        val textExpirationDate = it.expirationDate?.getStringDateFromLong("dd.MM.YYYY HH:MM") ?: "-"

                        CredentialItemUiModel(
                            expirationDate = "Expires: ${textExpirationDate}",
                            credentialType = it.credentialType
                        )
                    }

                    CredentialListUiModel.Success(uiCredentials)
                }

                is CredentialsListResModel.Fail -> {
                    CredentialListUiModel.Fail(res.errorType)
                }
                else -> {
                    throw RuntimeException("Unknown UI model type")
                }
            }

            withContext(Dispatchers.Main) {
                credentialsLiveData.value = credentialListUiModel
            }
        }
    }

    fun getCredentialsLiveData(): LiveData<CredentialListUiModel> {
        return credentialsLiveData
    }
}