package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialByIdUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialDetailsUi
import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialToDetailsModelMapper
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationModelUi
import by.alexandr7035.affinidi_id.presentation.common.credentials.verification.VerificationResultToUiMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CredentialDetailsViewModel @Inject constructor(
    private val getCredentialByIdUseCase: GetCredentialByIdUseCase,
    private val verifyCredentialUseCase: VerifyCredentialUseCase,
    private val verificationResultToUiMapper: VerificationResultToUiMapper,
    private val credentialToDetailsModelMapper: CredentialToDetailsModelMapper,
) : ViewModel() {

    private val credentialLiveData = MutableLiveData<CredentialDetailsUi>()
    private val verificationLiveData = SingleLiveEvent<VerificationModelUi>()

    init {
        Timber.debug("shared viewmodel initialized")
    }

    fun loadCredential(credentialId: String) {
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