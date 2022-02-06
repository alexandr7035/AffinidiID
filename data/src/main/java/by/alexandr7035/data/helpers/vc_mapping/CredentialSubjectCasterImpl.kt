package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.UnknownCredentialSubjectData
import by.alexandr7035.data.model.CredentialSubject
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.lang.RuntimeException
import javax.inject.Inject

class CredentialSubjectCasterImpl @Inject constructor(private val gson: Gson) : CredentialSubjectCaster {

    override fun getCredentialSubjectFromCredentialSubjectData(credentialContextUrl: String, credentialSubjectData: CredentialSubjectData): CredentialSubject {
        val jsonString = when (credentialContextUrl) {
            EMAIL_CREDENTIAL_TYPE_CONTEXT -> {
                gson.toJson(credentialSubjectData, EmailCredentialSubjectData::class.java)
            }
            else -> {
                throw RuntimeException("Unsupported VC type has to be issued")
            }
        }

        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return CredentialSubject(data = jsonObject)
    }

    // Specify all the supported VC types here
    companion object {
        private const val EMAIL_CREDENTIAL_TYPE_CONTEXT = "https://schema.affinidi.com/EmailCredentialV1-1.jsonld"
    }
}