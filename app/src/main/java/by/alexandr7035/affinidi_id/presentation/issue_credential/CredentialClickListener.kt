package by.alexandr7035.affinidi_id.presentation.issue_credential

import by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types.AvailableVcType

interface CredentialClickListener {
    fun onClick(availableVcType: AvailableVcType)
}