package by.alexandr7035.affinidi_id.presentation.scan_credential_qr

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.get_from_qr_code.ObtainVcFromQrCodeResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.ObtainCredentialWithQrCodeUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialToDetailsModelMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationModelUi
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationResultToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerifyCredentialQrViewModel @Inject constructor(
    private val obtainCredentialWithQrCodeUseCase: ObtainCredentialWithQrCodeUseCase,
    private val credentialToDetailsModelMapper: CredentialToDetailsModelMapper,
    private val verifyCredentialUseCase: VerifyCredentialUseCase,
    private val verificationResultToUiMapper: VerificationResultToUiMapper,
) : ViewModel() {

    private val credentialLiveData = MutableLiveData<CredentialDetailsUi>()
    private val verificationLiveData = SingleLiveEvent<VerificationModelUi>()

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

    fun verifyCredential(rawVc: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = verifyCredentialUseCase.execute(
                VerifyVcReqModel(
                    rawVc = rawVc
                )
            )

            withContext(Dispatchers.Main) {
                // Map domain result to ui model for verification snackbar
                verificationLiveData.value = verificationResultToUiMapper.map(res)
            }
        }
    }

    fun getCredentialLiveData(): LiveData<CredentialDetailsUi> = credentialLiveData

    fun getVerificationLiveData(): LiveData<VerificationModelUi> = verificationLiveData
}