package by.alexandr7035.affinidi_id.presentation.verify_credential

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationModelUi
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationResultToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(
    private val verifyCredentialUseCase: VerifyCredentialUseCase,
    private val verificationResultToUiMapper: VerificationResultToUiMapper,
): ViewModel() {

    private val verificationLiveData = SingleLiveEvent<VerificationModelUi>()

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

    fun getVerificationLiveData(): LiveData<VerificationModelUi> = verificationLiveData
}