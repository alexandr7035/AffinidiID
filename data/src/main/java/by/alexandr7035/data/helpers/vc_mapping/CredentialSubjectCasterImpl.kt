package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.UnknownCredentialSubject
import by.alexandr7035.data.model.credentials.common.UndefinedCredentialSubject
import com.google.gson.Gson
import java.lang.Exception
import javax.inject.Inject

class CredentialSubjectCasterImpl @Inject constructor(private val gson: Gson): CredentialSubjectCaster {
    override fun castToCredentialSubject(credentialType: String, credentialSubject: UndefinedCredentialSubject): CredentialSubject {
        try {
            return when (credentialType) {
                EMAIL_CREDENTIAL_TYPE -> {
                    // FIXME serialize json
                    EmailCredentialSubject("com@example.com")
                }
                else -> UnknownCredentialSubject()
            }
        }
        catch (e: Exception) {
            return UnknownCredentialSubject()
        }
    }


    // Specify all the supported values here
    companion object {
        private const val EMAIL_CREDENTIAL_TYPE = "EmailCredential"
    }
}