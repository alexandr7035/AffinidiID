package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.verify_vc.VerifyVcResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialByIdUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.VerifyCredentialUseCase
import by.alexandr7035.affinidi_id.presentation.common.SnackBarMode
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_metadata.CredentialMetadataToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_proof.CredentialProofToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.credential_details.credential_subject.CredentialSubjectToFieldsMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialTypeMapper
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CredentialDetailsViewModel @Inject constructor(
    private val getCredentialByIdUseCase: GetCredentialByIdUseCase,
    private val verifyCredentialUseCase: VerifyCredentialUseCase,
    private val resourceProvider: ResourceProvider,
    private val credentialStatusMapper: CredentialStatusMapper,
    private val credentialTypeMapper: CredentialTypeMapper,
    private val credentialSubjectToFieldsMapper: CredentialSubjectToFieldsMapper,
    private val credentialMetadataToFieldsMapper: CredentialMetadataToFieldsMapper,
    private val credentialProofToFieldsMapper: CredentialProofToFieldsMapper
) : ViewModel() {

    private val credentialLiveData = MutableLiveData<CredentialDetailsUiModel>()
    private val verificationLiveData = SingleLiveEvent<VerificationModelUi>()

    fun loadCredential(credentialId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            // Get credential
            getCredentialByIdUseCase.execute(
                GetCredentialByIdReqModel(credentialId = credentialId)
            ).collect { res ->

                val fieldsList = when (res) {
                    is GetCredentialByIdResModel.Success -> {

                        val credentialStatusUi = credentialStatusMapper.map(res.credential.credentialStatus)
                        val credentialType = credentialTypeMapper.map(res.credential.vcType)

                        val metadataItems = credentialMetadataToFieldsMapper.map(res.credential)
                        val credentialProofItems = credentialProofToFieldsMapper.map(res.credential)

                        // TODO handle errors inside
                        val gson = GsonBuilder().setPrettyPrinting().create()

                        val jsonObject = gson.fromJson(res.credential.credentialSubjectData, JsonObject::class.java)
                        val credentialSubjectItems = credentialSubjectToFieldsMapper.map(jsonObject)


                        val json = gson.fromJson(res.credential.rawVCData, JsonObject::class.java)
                        val prettyFormattedVC = gson.toJson(json, JsonObject::class.java)


                        CredentialDetailsUiModel.Success(
                            metadataItems = metadataItems,
                            credentialSubjectItems = credentialSubjectItems,
                            credentialType = credentialType,
                            credentialId = res.credential.id,
                            rawVcDataPrettyFormatted = prettyFormattedVC,
                            proofItems = credentialProofItems,
                            credentialStatus = credentialStatusUi
                        )

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