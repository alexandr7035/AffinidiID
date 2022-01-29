package by.alexandr7035.affinidi_id.presentation.delete_credential

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.usecase.credentials.DeleteCredentialUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DeleteCredentialViewModel @Inject constructor(private val deleteCredentialUseCase: DeleteCredentialUseCase) : ViewModel() {

    private val deleteVcLiveData = MutableLiveData<DeleteVcResModel>()

    fun deleteVc(credentialID: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = deleteCredentialUseCase.execute(DeleteVcReqModel(
                credentialId = credentialID
            ))

            withContext(Dispatchers.Main) {
                deleteVcLiveData.value = res
            }
        }
    }

    fun getDeleteVcLiveData(): LiveData<DeleteVcResModel> {
        return deleteVcLiveData
    }
}