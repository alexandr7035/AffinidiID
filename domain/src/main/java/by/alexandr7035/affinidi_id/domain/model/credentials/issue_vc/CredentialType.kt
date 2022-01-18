package by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject

abstract class CredentialType {

    abstract val credentialSubject: CredentialSubject
    abstract val typeName: String
    abstract val jsonLdContextUrl: String

    data class EmailVC(
        override val credentialSubject: EmailCredentialSubject,
        override val typeName: String = "EmailCredential",
        override val jsonLdContextUrl: String = "https://schema.affinidi.com/EmailCredentialV1-0.jsonld"
    ) : CredentialType()
}