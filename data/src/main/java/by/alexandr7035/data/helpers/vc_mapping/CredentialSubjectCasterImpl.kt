package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.CredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.UnknownCredentialSubjectData
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.model.CredentialSubject
import com.google.gson.Gson
import com.google.gson.JsonObject
import timber.log.Timber
import java.lang.RuntimeException
import javax.inject.Inject

class CredentialSubjectCasterImpl @Inject constructor(private val gson: Gson) : CredentialSubjectCaster {
    override fun castToCredentialSubjectData(credentialType: String, credentialSubject: CredentialSubject): CredentialSubjectData {
        Timber.debug("credentialSubject $credentialSubject")

        try {
            return when (credentialType) {
                EMAIL_CREDENTIAL_TYPE -> {
                    Timber.debug("json data $credentialSubject")
                    val emailVc = gson.fromJson(credentialSubject.data, EmailCredentialSubjectData::class.java)
                    Timber.debug("${emailVc}")
                    emailVc
                }
                else -> UnknownCredentialSubjectData()
            }
        } catch (e: Exception) {
            Timber.debug("exception in cast credentials ${e.message}")
            return UnknownCredentialSubjectData()
        }
    }

    override fun getCredentialSubjectFromCredentialSubjectData(credentialType: String, credentialSubjectData: CredentialSubjectData): CredentialSubject {
        Timber.debug("issue credential class data ${credentialSubjectData}")

        val jsonString = when (credentialType) {
            EMAIL_CREDENTIAL_TYPE -> {
                gson.toJson(credentialSubjectData, EmailCredentialSubjectData::class.java)
            }
            else -> {
                throw RuntimeException("Unsupported VC type has to be issued")
            }
        }

        val jsonObject = gson.fromJson(jsonString, JsonObject::class.java)
        return CredentialSubject(data = jsonObject)
    }

    // TODO use jsonld contexts here
    // Specify all the supported VC types here
    companion object {
        private const val EMAIL_CREDENTIAL_TYPE = "EmailCredential"
    }
}