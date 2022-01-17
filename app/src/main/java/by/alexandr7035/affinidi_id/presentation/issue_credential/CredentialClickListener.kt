package by.alexandr7035.affinidi_id.presentation.issue_credential

import by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials.AvailableVcType

interface CredentialClickListener {
    fun onClick(availableVcType: AvailableVcType)
}