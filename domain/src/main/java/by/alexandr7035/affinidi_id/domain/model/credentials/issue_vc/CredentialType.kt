package by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData

abstract class CredentialType {

    abstract val credentialSubjectData: CredentialSubjectData
    abstract val typeName: String
    abstract val jsonLdContextUrl: String

    data class EmailVC(
        override val credentialSubjectData: EmailCredentialSubjectData,
        override val typeName: String = "EmailCredential",
//        override val jsonLdContextUrl: String = "https://schema.affinidi.com/EmailCredentialV1-0.jsonld"
        override val jsonLdContextUrl: String = "https://schema.affinidi.com/EmailCredentialV1-1.jsonld"
    ) : CredentialType()
}