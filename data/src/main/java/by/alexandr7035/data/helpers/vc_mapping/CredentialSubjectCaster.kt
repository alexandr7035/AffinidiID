package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import by.alexandr7035.data.model.credentials.common.UndefinedCredentialSubject


interface CredentialSubjectCaster {
    fun castToCredentialSubject(credentialType: String, credentialSubject: UndefinedCredentialSubject): CredentialSubject
}