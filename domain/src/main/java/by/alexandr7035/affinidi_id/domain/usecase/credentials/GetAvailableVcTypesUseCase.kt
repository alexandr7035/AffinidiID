package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.available_credential_types.AvailableCredentialTypeModel
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType

class GetAvailableVcTypesUseCase {
    fun execute(): List<AvailableCredentialTypeModel> {
        // TODO use data layer to get access to string resource
        return listOf(
            AvailableCredentialTypeModel(
                typeName = "EmailCredential",
                description = "This VC proves the ownership of e-mail address used as login in AffinidiID.",
                issuer = "self-claimed",
                vcType = VcType.EMAIL_CREDENTIAL
            )
        )
    }
}