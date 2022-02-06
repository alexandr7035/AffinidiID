package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.core.extensions.getUnixDateFromStringFormat
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.model.SignedCredential
import com.google.gson.Gson
import com.google.gson.JsonObject
import timber.log.Timber
import javax.inject.Inject

// Cast credentials received from wallet to available domain types
class SignedCredentialToDomainMapperImpl @Inject constructor(
    private val gson: Gson
): SignedCredentialToDomainMapper {
    override fun map(signedCredential: SignedCredential): Credential {

        val expirationDate = signedCredential.expirationDate?.getUnixDateFromStringFormat(CREDENTIAL_DATE_FORMAT)
        val issuanceDate = signedCredential.issuanceDate.getUnixDateFromStringFormat(CREDENTIAL_DATE_FORMAT)

        val credentialStatus = if (expirationDate != null) {
            if (expirationDate < System.currentTimeMillis()) {
                CredentialStatus.EXPIRED
            } else {
                CredentialStatus.ACTIVE
            }
        } else {
            CredentialStatus.ACTIVE
        }

        val credentialSubjectString = gson.toJson(signedCredential.credentialSubject.data, JsonObject::class.java)

        // Prepare raw VC
        val rawVc = gson.toJson(signedCredential, SignedCredential::class.java)
        Timber.debug("RAW VC DATA $rawVc")

        // Domain credential proof
        val proof = CredentialProof(
            type = signedCredential.proof.type,
            creationDate = signedCredential.proof.creationDate,
            verificationMethod = signedCredential.proof.verificationMethod,
            proofPurpose = signedCredential.proof.proofPurpose,
            jws = signedCredential.proof.jws
        )

        return Credential(
            id = signedCredential.id,
            vcType = signedCredential.type.last(),
            expirationDate = expirationDate,
            issuanceDate = issuanceDate,
            holderDid = signedCredential.holder.holderDid,
            issuerDid = signedCredential.issuerDid,
            credentialStatus = credentialStatus,
            credentialSubjectData = credentialSubjectString,
            credentialProof = proof,
            rawVCData = rawVc
        )
    }

    companion object {
        private const val CREDENTIAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}