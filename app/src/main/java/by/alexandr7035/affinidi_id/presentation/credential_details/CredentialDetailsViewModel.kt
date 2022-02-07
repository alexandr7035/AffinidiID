package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialByIdUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialToDetailsModelMapper
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDetailsUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialDetailsViewModel @Inject constructor(
    private val getCredentialByIdUseCase: GetCredentialByIdUseCase,
    private val verifyCredentialUseCase: VerifyCredentialUseCase,
    private val resourceProvider: ResourceProvider,
    private val credentialToDetailsModelMapper: CredentialToDetailsModelMapper,
) : ViewModel() {

    private val credentialLiveData = MutableLiveData<CredentialDetailsUiModel>()
    private val verificationLiveData = SingleLiveEvent<VerificationModelUi>()

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
                        CredentialDetailsUiModel.Loading
                    }

                    is GetCredentialByIdResModel.Fail -> {
                        CredentialDetailsUiModel.Fail(res.errorType)
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

            val verificationUiModel = when (res) {
                is VerifyVcResModel.Success -> {
                    val uiVerificationModel = when (res.isValid) {
                        true -> {
                            VerificationModelUi.Success(
                                validationResultSnackBarMode = SnackBarMode.Positive,
                                messageText = resourceProvider.getString(R.string.vc_is_valid),
                            )
                        }
                        false -> {
                            VerificationModelUi.Success(
                                validationResultSnackBarMode = SnackBarMode.Negative,
                                messageText = resourceProvider.getString(R.string.vc_is_not_valid),
                            )
                        }
                    }

                    uiVerificationModel
                }

                is VerifyVcResModel.Fail -> {
                    VerificationModelUi.Fail(errorType = res.errorType)
                }
            }


            withContext(Dispatchers.Main) {
                verificationLiveData.value = verificationUiModel
            }
        }
    }

    fun getCredentialLiveData(): LiveData<CredentialDetailsUiModel> {
        return credentialLiveData
    }

    fun getVerificationLiveData(): LiveData<VerificationModelUi> {
        return verificationLiveData
    }
}