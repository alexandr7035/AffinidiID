package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.UnknownCredentialSubject
import by.alexandr7035.data.extensions.debug
import com.google.gson.Gson
import com.google.gson.JsonObject
import timber.log.Timber
import javax.inject.Inject

class CredentialSubjectCasterImpl @Inject constructor(private val gson: Gson): CredentialSubjectCaster {
    override fun castToCredentialSubject(credentialType: String, credentialSubject: JsonObject): CredentialSubject {
        Timber.debug("credentialSubject $credentialSubject")

        try {
            return when (credentialType) {
                EMAIL_CREDENTIAL_TYPE -> {
                    Timber.debug("json $credentialSubject")
                    val emailVc = gson.fromJson(credentialSubject, EmailCredentialSubject::class.java )
                    Timber.debug("${emailVc}")
                    emailVc
                }
                else -> UnknownCredentialSubject()
            }
        }
        catch (e: Exception) {
            Timber.debug("exception in cast credentials ${e.message}")
            return UnknownCredentialSubject()
        }
    }


    // Specify all the supported VC types here
    companion object {
        private const val EMAIL_CREDENTIAL_TYPE = "EmailCredential"
    }
}