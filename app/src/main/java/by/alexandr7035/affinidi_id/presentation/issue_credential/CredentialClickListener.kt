package by.alexandr7035.affinidi_id.presentation.issue_credential

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType

interface CredentialClickListener {
    fun onClick(availableVcType: VcType)
}