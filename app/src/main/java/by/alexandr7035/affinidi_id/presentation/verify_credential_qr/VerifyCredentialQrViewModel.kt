package by.alexandr7035.affinidi_id.presentation.verify_credential_qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.ObtainCredentialWithQrCodeUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyCredentialQrViewModel @Inject constructor(
    private val obtainCredentialWithQrCodeUseCase: ObtainCredentialWithQrCodeUseCase,
) : ViewModel() {

    // FIXME ui models
    private val credentialLiveData = SingleLiveEvent<ObtainVcFromQrCodeResModel>()

    fun obtainCredential(credentialLink: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = obtainCredentialWithQrCodeUseCase.execute(ObtainVcFromQrCodeReqModel(credentialLink))

            withContext(Dispatchers.Main) {
                credentialLiveData.value = res
            }
        }
    }

    fun getCredentialLiveData(): LiveData<ObtainVcFromQrCodeResModel> = credentialLiveData
}