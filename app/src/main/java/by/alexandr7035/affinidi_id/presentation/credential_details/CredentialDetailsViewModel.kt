package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialByIdUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
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
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val credentialLiveData = MutableLiveData<CredentialDetailsUiModel>()
    private val verificationLiveData = MutableLiveData<VerificationModelUi>()

    fun loadCredential(credentialId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Get credential
            getCredentialByIdUseCase.execute(
                GetCredentialByIdReqModel(credentialId = credentialId)
            ).collect { res ->

                val fieldsList = when (res) {
                    is GetCredentialByIdResModel.Success -> {

                        // Cut DID after ";" (initial state, etc.)
                        val formattedIssuerDid = res.credential.issuerDid.split(";").first()

                        val dataItems = listOf(
                            CredentialDataItem.Spacing(),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.credential_id),
                                value = res.credential.id
                            ),
                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.issuer_did),
                                value = formattedIssuerDid
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.holder_did),
                                value = res.credential.holderDid
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.issuance_date),
                                value = res.credential.issuanceDate.getStringDateFromLong(DATE_FORMAT)
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.expiration_date),
                                value = res.credential.expirationDate?.getStringDateFromLong(DATE_FORMAT)
                                    ?: resourceProvider.getString(R.string.no_expiration)
                            )
                        )

                        CredentialDetailsUiModel.Success(detailsItems = dataItems, credentialId = res.credential.id)
                    }
                    is GetCredentialByIdResModel.Loading -> {
                        CredentialDetailsUiModel.Loading
                    }

                    is GetCredentialByIdResModel.Fail -> {
                        CredentialDetailsUiModel.Fail(res.errorType)
                    }
                }

                withContext(Dispatchers.Main) {
                    credentialLiveData.value = fieldsList
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

            val uiVerificationModel = when (res.isValid) {
                true -> {
                    VerificationModelUi(
                        isValid = res.isValid,
                        messageText = resourceProvider.getString(R.string.vc_is_valid),
                    )
                }
                false -> {
                    VerificationModelUi(
                        isValid = res.isValid,
                        messageText = resourceProvider.getString(R.string.vc_is_not_valid),
                    )
                }
            }

            withContext(Dispatchers.Main) {
                verificationLiveData.value = uiVerificationModel
            }
        }
    }

    fun getCredentialLiveData(): LiveData<CredentialDetailsUiModel> {
        return credentialLiveData
    }

    fun getVerificationLiveData(): LiveData<VerificationModelUi> {
        return verificationLiveData
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:SS"
    }
}