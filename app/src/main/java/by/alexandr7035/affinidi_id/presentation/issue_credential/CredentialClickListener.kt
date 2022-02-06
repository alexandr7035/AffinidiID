package by.alexandr7035.affinidi_id.presentation.issue_credential

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssuingCredentialType

interface CredentialClickListener {
    fun onClick(availableIssuingCredentialType: IssuingCredentialType)
}