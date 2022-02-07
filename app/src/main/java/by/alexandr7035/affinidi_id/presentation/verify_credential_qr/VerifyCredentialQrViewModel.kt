package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.ObtainCredentialWithQrCodeUseCase
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDetailsUiModel
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialToDetailsModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyCredentialQrViewModel @Inject constructor(
    private val obtainCredentialWithQrCodeUseCase: ObtainCredentialWithQrCodeUseCase,
    private val credentialToDetailsModelMapper: CredentialToDetailsModelMapper
) : ViewModel() {

    // FIXME ui models
    private val credentialLiveData = MutableLiveData<CredentialDetailsUiModel>()

    fun obtainCredential(credentialLink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = obtainCredentialWithQrCodeUseCase.execute(ObtainVcFromQrCodeReqModel(credentialLink))


            // TODO loading state
            val uiModel = when (res) {
                is ObtainVcFromQrCodeResModel.Success -> {
                    credentialToDetailsModelMapper.map(credential = res.credential)
                }

                is ObtainVcFromQrCodeResModel.Fail -> {
                    CredentialDetailsUiModel.Fail(res.errorType)
                }
            }

            withContext(Dispatchers.Main) {
                credentialLiveData.value = uiModel
            }
        }
    }

    fun getCredentialLiveData(): LiveData<CredentialDetailsUiModel> = credentialLiveData
}