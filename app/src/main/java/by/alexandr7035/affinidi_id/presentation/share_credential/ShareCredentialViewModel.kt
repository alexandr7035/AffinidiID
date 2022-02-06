package by.alexandr7035.affinidi_id.presentation.share_credential

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.core.livedata.SingleLiveEvent
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.ShareCredentialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ShareCredentialViewModel @Inject constructor(
    private val shareCredentialUseCase: ShareCredentialUseCase
) : ViewModel() {

    private val shareCredentialLiveData = SingleLiveEvent<ShareCredentialResModel>()

    fun load(credentialId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = shareCredentialUseCase.execute(ShareCredentialReqModel(credentialId))

            withContext(Dispatchers.Main) {
                shareCredentialLiveData.value = res
            }
        }
    }

    fun getShareCredentialLiveData(): LiveData<ShareCredentialResModel> {
        return shareCredentialLiveData
    }
}