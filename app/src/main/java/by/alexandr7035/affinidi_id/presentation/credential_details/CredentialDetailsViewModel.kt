package by.alexandr7035.affinidi_id.presentation.credential_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.usecase.credentials.GetCredentialDetailsUseCase
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CredentialDetailsViewModel @Inject constructor(
    private val getCredentialDetailsUseCase: GetCredentialDetailsUseCase,
    private val resourceProvider: ResourceProvider
): ViewModel() {
    private val credentialLiveData = MutableLiveData<List<CredentialDataItem>>()

    fun load() {
        viewModelScope.launch(Dispatchers.IO) {
            val res = getCredentialDetailsUseCase.execute()

            val fieldsList = listOf(
                CredentialDataItem.Spacing(),

                CredentialDataItem.Field(
                    name = resourceProvider.getString(R.string.credential_id),
                    value = res.id
                ),
                CredentialDataItem.Field(
                    name = resourceProvider.getString(R.string.issuer_did),
                    value = res.issuerDid
                ),
                CredentialDataItem.Spacing(),

                CredentialDataItem.Field(
                    name = resourceProvider.getString(R.string.holder_did),
                    value = res.holderDid
                ),

                CredentialDataItem.Field(
                    name = resourceProvider.getString(R.string.issuance_date),
                    value = res.issuanceDate.getStringDateFromLong(DATE_FORMAT)
                ),

                CredentialDataItem.Field(
                    name = resourceProvider.getString(R.string.expiration_date),
                    value = res.expirationDate?.getStringDateFromLong(DATE_FORMAT) ?: resourceProvider.getString(R.string.no_expiration)
                ),
            )

            withContext(Dispatchers.Main) {
                credentialLiveData.value = fieldsList
            }
        }
    }

    fun getCredentialLiveData(): LiveData<List<CredentialDataItem>> {
        return credentialLiveData
    }

    companion object {
        private const val DATE_FORMAT = "dd MMM yyyy HH:mm:SS"
    }
}