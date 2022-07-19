package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialByIdUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.ObtainCredentialWithQrCodeUseCase
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialToDetailsModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoadCredentialDetailsViewModel @Inject constructor(
    private val getCredentialByIdUseCase: GetCredentialByIdUseCase,
    private val obtainCredentialWithQrCodeUseCase: ObtainCredentialWithQrCodeUseCase,
    private val credentialToDetailsModelMapper: CredentialToDetailsModelMapper,
) : ViewModel() {
    private val credentialLiveData = MutableLiveData<CredentialDetailsUi>()

    fun loadCredentialById(credentialId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Get credential
            getCredentialByIdUseCase.execute(
                GetCredentialByIdReqModel(credentialId = credentialId)
            ).collect { res ->

                val credentialDetails = when (res) {
                    is GetCredentialByIdResModel.Success -> {
                        credentialToDetailsModelMapper.map(credential = res.credential)
                    }
                    is GetCredentialByIdResModel.Loading -> {
                        CredentialDetailsUi.Loading
                    }

                    is GetCredentialByIdResModel.Fail -> {
                        CredentialDetailsUi.Fail(res.errorType)
                    }
                }

                withContext(Dispatchers.Main) {
                    credentialLiveData.value = credentialDetails
                }
            }
        }
    }

    fun obtainCredential(credentialLink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = obtainCredentialWithQrCodeUseCase.execute(ObtainVcFromQrCodeReqModel(credentialLink))

            val uiModel = when (res) {
                is ObtainVcFromQrCodeResModel.Success -> {
                    credentialToDetailsModelMapper.map(credential = res.credential)
                }

                is ObtainVcFromQrCodeResModel.Fail -> {
                    CredentialDetailsUi.Fail(res.errorType)
                }
            }

            withContext(Dispatchers.Main) {
                credentialLiveData.value = uiModel
            }
        }
    }

    fun getCredentialLiveData(): LiveData<CredentialDetailsUi> = credentialLiveData
}