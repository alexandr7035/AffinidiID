package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.data.model.SignedCredential

interface SignedCredentialToDomainMapper {
    fun map(signedCredential: SignedCredential): Credential
}