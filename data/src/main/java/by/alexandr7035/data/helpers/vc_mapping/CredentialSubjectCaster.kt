package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData
import by.alexandr7035.data.model.CredentialSubject


interface CredentialSubjectCaster {
    fun castToCredentialSubjectData(credentialType: String, credentialSubject: CredentialSubject): CredentialSubjectData

    fun getCredentialSubjectFromCredentialSubjectData(credentialType: String, credentialSubjectData: CredentialSubjectData): CredentialSubject
}