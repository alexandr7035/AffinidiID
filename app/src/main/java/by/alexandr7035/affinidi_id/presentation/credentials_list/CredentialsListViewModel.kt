package by.alexandr7035.affinidi_id.presentation.credentials_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialsListUseCase
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialsListViewModel @Inject constructor(
    private val getCredentialsListUseCase: GetCredentialsListUseCase,
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

                        val textExpirationDate = it.expirationDate?.getStringDateFromLong("dd.MM.YYYY HH:mm") ?: resourceProvider.getString(
                            R.string.no_expiration
                        )

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

                        val credentialType = when (it.vcType) {
                            VcType.EMAIL_CREDENTIAL -> {
                                resourceProvider.getString(R.string.vc_type_email)
                            }
                            else -> {
                                resourceProvider.getString(R.string.vc_type_unknown)
                            }
                        }

                        val vcFields = when (it.vcType) {
                            VcType.EMAIL_CREDENTIAL -> {
                                val vcSubject = it.credentialSubject as EmailCredentialSubject
                                listOf(
                                    VCFieldItem(
                                        type = resourceProvider.getString(R.string.address),
                                        value = vcSubject.email
                                    )
                                )
                            }
                            else -> {
                                emptyList()
                            }
                        }

                        CredentialItemUiModel(
                            id = it.id,
                            expirationDate = textExpirationDate,
                            credentialTypeString = credentialType,
                            credentialStatus = credentialStatusText,
                            statusMarkColor = statusMarkColor,
                            vcFields = vcFields
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

}