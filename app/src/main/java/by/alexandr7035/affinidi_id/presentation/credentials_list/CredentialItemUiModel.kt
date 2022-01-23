package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.presentation.common.CredentialStatusUi
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val isUnknownType: Boolean,
    val vcFields: List<VCFieldItem>,
    val expirationDate: String,
    val credentialStatus: CredentialStatusUi,
)