package by.alexandr7035.affinidi_id.presentation.helpers.mappers

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType

interface CredentialTypeMapper {
    fun map(vcType: VcType): String
}