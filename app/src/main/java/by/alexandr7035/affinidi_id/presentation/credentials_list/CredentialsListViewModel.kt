package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.BuildCredentialType
import by.alexandr7035.affinidi_id.domain.model.credentials.unsigned_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.BuildUnsignedVcObjectUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialsListUseCase
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialsListViewModel @Inject constructor(
    private val getCredentialsListUseCase: GetCredentialsListUseCase,
    private val buildUnsignedVcObjectUseCase: BuildUnsignedVcObjectUseCase,
    private val resourceProvider: ResourceProvider
) : ViewModel() {

    private val credentialsLiveData = MutableLiveData<CredentialListUiModel>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCredentialsListUseCase.execute()

            val credentialListUiModel: CredentialListUiModel = when (res) {
                is CredentialsListResModel.Success -> {

                    // TODO mapper
                    val uiCredentials: List<CredentialItemUiModel> = res.credentials.map {

                        val textExpirationDate = it.expirationDate?.getStringDateFromLong("dd.MM.YYYY HH:MM") ?: resourceProvider.getString(R.string.no_expiration)

                        val credentialStatusText = when (it.credentialStatus) {
                            CredentialStatus.ACTIVE -> {
                                resourceProvider.getString(R.string.active)
                            }
                            CredentialStatus.EXPIRED -> {
                                resourceProvider.getString(R.string.expired)
                            }
                        }

                        val statusMarkColor = when (it.credentialStatus) {
                            CredentialStatus.ACTIVE -> {
                                resourceProvider.getColor(R.color.active_vc_mark)
                            }
                            CredentialStatus.EXPIRED -> {
                                resourceProvider.getColor(R.color.inactive_vc_mark)
                            }
                        }

                        CredentialItemUiModel(
                            id = it.id,
                            expirationDate = textExpirationDate,
                            credentialType = it.credentialType,
                            credentialStatus = credentialStatusText,
                            statusMarkColor = statusMarkColor
                        )
                    }

                    CredentialListUiModel.Success(uiCredentials)
                }

                is CredentialsListResModel.Fail -> {
                    CredentialListUiModel.Fail(res.errorType)
                }
                else -> {
                    throw RuntimeException("Unknown UI model type")
                }
            }

            withContext(Dispatchers.Main) {
                credentialsLiveData.value = credentialListUiModel
            }
        }
    }

    fun getCredentialsLiveData(): LiveData<CredentialListUiModel> {
        return credentialsLiveData
    }


    fun testBuildUnsigned() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = buildUnsignedVcObjectUseCase.execute(
                IssueCredentialReqModel(
                    buildCredentialType = BuildCredentialType.EmailVC(
                        credentialSubject = EmailCredentialSubject(email = "testmail@mailto.plus")
                    ),
                    expiresAt = 0,
                    holderDid = "did:elem:test"
                )
            )
        }
    }
}