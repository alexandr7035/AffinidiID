package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class GetCredentialDetailsUseCase @Inject constructor(private val credentialsRepository: CredentialsRepository, authStateUseCase: GetAuthStateUseCase) {
    fun execute(): Credential {
        return Credential(
            credentialStatus = CredentialStatus.ACTIVE,
            vcType = VcType.EMAIL_CREDENTIAL,
            credentialSubject = EmailCredentialSubject(
                email = "cv"
            ),
            expirationDate = 0,
            holderDid = "did:elem:234324224",
            id = "claimId:43242Sfddfdsf",
            issuanceDate = 0,
            issuerDid = "did:elem:343423324dfsdfD",
            credentialProof = CredentialProof(
                "", "", "", "", ""
            )
        )
    }
}