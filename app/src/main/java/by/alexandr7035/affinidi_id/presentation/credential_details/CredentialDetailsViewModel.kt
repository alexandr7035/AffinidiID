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
    private val credentialSubjectToFieldsMapper: CredentialSubjectToFieldsMapper
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

                        // Cut DID after ";" (initial state, etc.)
                        val formattedIssuerDid = res.credential.issuerDid.split(";").first()
                        val credentialStatusUi = credentialStatusMapper.map(res.credential.credentialStatus)
                        val credentialType = credentialTypeMapper.map(res.credential.vcType)

                        val dataItems = listOf(

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.credential_id),
                                value = res.credential.id
                            ),
                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.issuer),
                                value = formattedIssuerDid
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.holder),
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

                        val gson = GsonBuilder().setPrettyPrinting().create()
                        val json = gson.fromJson(res.credential.rawVCData, JsonObject::class.java)
                        val prettyFormattedVC = gson.toJson(json, JsonObject::class.java)

                        // DEBUG
                        // FIXME remove
//                        val testJsonString = "{\"email\":\"clean_ref@mailto.plus\", \"name\":\"Name\"}"

                        // TODO handle errors inside
                        val jsonObject = gson.fromJson(res.credential.credentialSubjectData, JsonObject::class.java)
                        val credentialSubjectItems = credentialSubjectToFieldsMapper.map(jsonObject)

                        val credentialProofItems = listOf(
                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.created),
                                value = res.credential.credentialProof.creationDate,
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.type),
                                value = res.credential.credentialProof.type,
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.proof_purpose),
                                value = res.credential.credentialProof.proofPurpose,
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.verification_method),
                                value = res.credential.credentialProof.verificationMethod,
                            ),

                            CredentialDataItem.Field(
                                name = resourceProvider.getString(R.string.jws),
                                value = res.credential.credentialProof.jws,
                            ),
                        )

                        CredentialDetailsUiModel.Success(
                            metadataItems = dataItems,
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

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:SS"
    }
}