package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val vcFields: List<VCFieldItem>,
    val expirationDate: String,
    val credentialStatus: String,
    val statusMarkColor: Int
)