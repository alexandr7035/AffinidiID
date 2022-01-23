package by.alexandr7035.affinidi_id.presentation.helpers.mappers

import by.alexandr7035.affinidi_id.R
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.presentation.helpers.resources.ResourceProvider
import javax.inject.Inject

class CredentialTypeMapperImpl @Inject constructor(private val resourceProvider: ResourceProvider): CredentialTypeMapper {
    override fun map(vcType: VcType): String {
        return when (vcType) {
            VcType.EMAIL_CREDENTIAL -> {
                resourceProvider.getString(R.string.vc_type_email)
            }
            else -> {
                resourceProvider.getString(R.string.vc_type_unknown)
            }
        }
    }
}