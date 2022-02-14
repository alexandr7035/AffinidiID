package by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.credential_subject.CredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.credential_subject.EmailCredentialSubjectData

abstract class IssuingCredentialData {

    abstract val credentialSubjectData: CredentialSubjectData
    abstract val typeName: String
    abstract val jsonLdContextUrl: String

    data class EmailVC(
        override val credentialSubjectData: EmailCredentialSubjectData,
        override val typeName: String = "EmailCredential",
        override val jsonLdContextUrl: String = "https://schema.affinidi.com/EmailCredentialV1-1.jsonld"
    ) : IssuingCredentialData()
}