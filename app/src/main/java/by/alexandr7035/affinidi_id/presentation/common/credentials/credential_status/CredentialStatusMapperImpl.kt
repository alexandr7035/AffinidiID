package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_status

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.presentation.common.resources.ResourceProvider
import javax.inject.Inject

class CredentialStatusMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider) : CredentialStatusMapper {
    override fun map(credentialStatus: CredentialStatus): CredentialStatusUi {
        val stringStatus = when (credentialStatus) {
            CredentialStatus.ACTIVE -> {
                resourceProvider.getString(R.string.active)
            }
            CredentialStatus.EXPIRED -> {
                resourceProvider.getString(R.string.expired)
            }
        }

        val statusColor = when (credentialStatus) {
            CredentialStatus.ACTIVE -> {
                resourceProvider.getColor(R.color.active_vc_mark)
            }
            CredentialStatus.EXPIRED -> {
                resourceProvider.getColor(R.color.inactive_vc_mark)
            }
        }

        return CredentialStatusUi(
            statusText = stringStatus,
            statusColor = statusColor,
            domainStatus = credentialStatus
        )
    }
}