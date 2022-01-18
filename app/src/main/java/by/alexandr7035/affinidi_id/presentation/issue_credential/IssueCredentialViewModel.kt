package by.alexandr7035.affinidi_id.presentation.issue_credential

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types.AvailableCredentialTypeModel
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.CredentialType
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetAvailableVcTypesUseCase
import by.alexandr7035.affinidi_id.domain.usecase.credentials.IssueCredentialUseCase
import by.alexandr7035.affinidi_id.domain.usecase.user.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class IssueCredentialViewModel @Inject constructor(
    private val getAvailableVcTypesUseCase: GetAvailableVcTypesUseCase,
    private val issueCredentialUseCase: IssueCredentialUseCase,
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {
    private val availableVCsLiveData = MutableLiveData<List<AvailableCredentialTypeModel>>()
    private val issueCredentialLiveData = MutableLiveData<IssueCredentialResModel>()

    fun loadAvailableVCs() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getAvailableVcTypesUseCase.execute()

            withContext(Dispatchers.Main) {
                availableVCsLiveData.value = res
            }
        }
    }

    fun issueCredential(credentialType: VcType) {
        val issueRequest = when (credentialType) {
            VcType.EMAIL_CREDENTIAL -> {

                val profile = getProfileUseCase.execute()

                IssueCredentialReqModel(
                    credentialType = CredentialType.EmailVC(
                        credentialSubject = EmailCredentialSubject(
                            // This is a email address
                            profile.userName
                        )
                    ),
                    holderDid = profile.userDid,
                    expiresAt = System.currentTimeMillis() + EXPIRATION_DATE_OFFSET
                )
            }
            else -> {
                throw RuntimeException("Can't issue unknown credential")
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            val res = issueCredentialUseCase.execute(issueRequest)

            withContext(Dispatchers.Main) {
                issueCredentialLiveData.value = res
            }
        }
    }

    fun getAvailableVCsLiveData(): LiveData<List<AvailableCredentialTypeModel>> {
        return availableVCsLiveData
    }

    fun getIssueCredentialLiveData(): LiveData<IssueCredentialResModel> {
        return issueCredentialLiveData
    }

    companion object {
        // 30 days
        // TODO user's choice
        private const val EXPIRATION_DATE_OFFSET = 24 * 60 * 60 * 30 * 1000L
    }
}