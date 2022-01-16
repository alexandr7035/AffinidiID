package by.alexandr7035.affinidi_id.domain.usecase.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.affinidi_id.domain.usecase.user.GetAuthStateUseCase
import javax.inject.Inject

class GetCredentialsListUseCase @Inject constructor(
    private val credentialsRepository: CredentialsRepository,
    private val authStateUseCase: GetAuthStateUseCase
) {
//    fun execute(): CredentialsListResModel {
//        return CredentialsListResModel.Success(
//            listOf(
//                Credential(
//                    "EmailCredential",
//                    "claimId:5a8cd8298028fc09",
//                    "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
//                    "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
//                    1234234334,
//                    null
//                ),
//
//                Credential(
//                    "EmailCredential",
//                    "claimId:5a8cd8298028fc09",
//                    "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
//                    "did:elem:EiCCU8V6WCpkGAgF_kjO5a5lZiWFXOFYA6KFV5PFtzHzew",
//                    1234234334,
//                    1234324234
//                )
//            )
//        )
//    }

    suspend fun execute(): CredentialsListResModel {
        return credentialsRepository.getAllCredentials(authState = authStateUseCase.execute())
    }
}