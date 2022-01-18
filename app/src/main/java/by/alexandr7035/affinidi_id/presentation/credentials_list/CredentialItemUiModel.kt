package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject

data class CredentialItemUiModel(
    val id: String,
    val credentialTypeString: String,
    val vcType: VcType,
    val credentialSubject: CredentialSubject,
    val expirationDate: String,
    val credentialStatus: String,
    val statusMarkColor: Int
)