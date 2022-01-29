package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData
import by.alexandr7035.data.model.CredentialSubject

// The value of the @context property MUST be an ordered set
// where the first item is a URI with the
// value https://www.w3.org/2018/credentials/v1
//
// So we can use at least LAST value in contexts array to detect VC type
interface CredentialSubjectCaster {
    fun castToCredentialSubjectData(credentialContextUrl: String, credentialSubject: CredentialSubject): CredentialSubjectData

    fun getCredentialSubjectFromCredentialSubjectData(credentialContextUrl: String, credentialSubjectData: CredentialSubjectData): CredentialSubject
}