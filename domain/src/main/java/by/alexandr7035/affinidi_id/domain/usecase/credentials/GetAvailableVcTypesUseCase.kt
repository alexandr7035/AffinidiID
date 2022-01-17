package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials.AvailableCredentialModel
import by.alexandr7035.affinidi_id.domain.model.credentials.available_credentials.AvailableVcType

class GetAvailableVcTypesUseCase {
    fun execute(): List<AvailableCredentialModel> {
        // TODO use data layer to get access to string resource
        return listOf(
            AvailableCredentialModel(
                typeName = "EmailCredential",
                description = "This VC proves the ownership of e-mail address used as login in AffinidiID.",
                issuer = "self-claimed",
                vcType = AvailableVcType.EMAIL_CREDENTIAL
            )
        )
    }
}