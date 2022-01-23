package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import javax.inject.Inject

class CredentialsListMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val credentialStatusMapper: CredentialStatusMapper
) : CredentialsListMapper {

    override fun map(domainCredentials: CredentialsListResModel): CredentialListUiModel {
        return when (domainCredentials) {
            is CredentialsListResModel.Success -> {

                val uiCredentials: List<CredentialItemUiModel> = domainCredentials.credentials.map {

                    val textExpirationDate =
                        it.expirationDate?.getStringDateFromLong("dd.MM.YYYY HH:mm") ?: resourceProvider.getString(
                            R.string.no_expiration
                        )

                    // To use different viewtype in recycler
                    val isUnknownVcType = it.vcType == VcType.UNKNOWN_CREDENTIAL

                    // To show different labels and colors fof VC statuses
                    val credentialStatus = credentialStatusMapper.map(it.credentialStatus)

                    val credentialType = when (it.vcType) {
                        VcType.EMAIL_CREDENTIAL -> {
                            resourceProvider.getString(R.string.vc_type_email)
                        }
                        else -> {
                            resourceProvider.getString(R.string.vc_type_unknown)
                        }
                    }

                    val vcFields = when (it.vcType) {
                        VcType.EMAIL_CREDENTIAL -> {
                            val vcSubject = it.credentialSubjectData as EmailCredentialSubjectData
                            listOf(
                                VCFieldItem(
                                    type = resourceProvider.getString(R.string.address),
                                    value = vcSubject.email
                                )
                            )
                        }
                        else -> {
                            emptyList()
                        }
                    }

                    CredentialItemUiModel(
                        id = it.id,
                        expirationDate = textExpirationDate,
                        credentialTypeString = credentialType,
                        credentialStatus = credentialStatus,
                        vcFields = vcFields,
                        isUnknownType = isUnknownVcType
                    )
                }

                CredentialListUiModel.Success(uiCredentials)
            }

            is CredentialsListResModel.Fail -> {
                CredentialListUiModel.Fail(domainCredentials.errorType)
            }

            is CredentialsListResModel.Loading -> {
                CredentialListUiModel.Loading()
            }

            else -> {
                throw RuntimeException("Unknown model type")
            }
        }
    }
}