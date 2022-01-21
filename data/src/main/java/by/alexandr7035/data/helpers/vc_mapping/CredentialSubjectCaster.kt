package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import com.google.gson.JsonObject


interface CredentialSubjectCaster {
    fun castToCredentialSubject(credentialType: String, credentialSubject: JsonObject): CredentialSubject

    fun credentialSubjectToJson(credentialSubject: CredentialSubject): String
}