package by.alexandr7035.affinidi_id.presentation.credentials_list

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.presentation.common.errors.ErrorTypeMapper
import by.alexandr7035.affinidi_id.presentation.credentials_list.vc_fields_recycler.VCFieldItem
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialStatusMapper
import by.alexandr7035.affinidi_id.presentation.helpers.mappers.CredentialTypeMapper
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import javax.inject.Inject

class CredentialsListMapperImpl @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val credentialStatusMapper: CredentialStatusMapper,
    private val credentialTypeMapper: CredentialTypeMapper,
    private val errorTypeMapper: ErrorTypeMapper
) : CredentialsListMapper {

    override fun map(domainCredentials: CredentialsListResModel): CredentialListUiModel {
        return when (domainCredentials) {
            is CredentialsListResModel.Success -> {

                val uiCredentials: List<CredentialItemUiModel> = domainCredentials.credentials.map {

                    val textExpirationDate =
                        it.expirationDate?.getStringDateFromLong(CARD_EXPIRATION_DATE_FORMAT) ?: resourceProvider.getString(
                            R.string.no_expiration
                        )

                    // To use different viewtype in recycler
                    val isUnknownVcType = it.vcType == VcType.UNKNOWN_CREDENTIAL

                    // Map domain fields to UI
                    val credentialStatus = credentialStatusMapper.map(it.credentialStatus)
                    val credentialType = credentialTypeMapper.map(vcType = it.vcType)

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
                CredentialListUiModel.Fail(errorUi = errorTypeMapper.map(errorType = domainCredentials.errorType))
            }

            is CredentialsListResModel.Loading -> {
                CredentialListUiModel.Loading
            }
        }
    }

    companion object {
        private const val CARD_EXPIRATION_DATE_FORMAT = "dd.MM.YYYY HH:mm"
    }
}