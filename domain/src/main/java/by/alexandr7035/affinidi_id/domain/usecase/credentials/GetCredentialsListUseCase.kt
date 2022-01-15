package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.Credential

class GetCredentialsListUseCase {
    fun execute(): List<Credential> {
        return listOf(
            Credential(
                "EmailCredential",
                "claimId:5a8cd8298028fc09",
                "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
                "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
                1234234334,
                null
            ),

            Credential(
                "EmailCredential",
                "claimId:5a8cd8298028fc09",
                "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
                "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
                1234234334,
                1234324234
            )
        )
    }
}