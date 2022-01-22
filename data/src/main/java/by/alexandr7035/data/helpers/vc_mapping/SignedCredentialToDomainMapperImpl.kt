package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.core.extensions.getUnixDateFromStringFormat
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.data.model.SignedCredential
import javax.inject.Inject

// Cast credentials received from wallet to available domain types
// TODO implement unknown credential type
class SignedCredentialToDomainMapperImpl @Inject constructor(private val credentialSubjectCaster: CredentialSubjectCaster): SignedCredentialToDomainMapper {
    override fun map(signedCredential: SignedCredential): Credential {
//
//        val gson  = GsonBuilder().setPrettyPrinting().create()
//        val json = gson.toJson(signedCredential, SignedCredential::class.java)
//        Timber.debug("RAW VC $json")
//
//        val signedNew = gson.fromJson(json, SignedCredential::class.java)
//        Timber.debug("RAW VC ${signedNew}")

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

        // Fist type will always be "VerifiableCredential"
        // Get last type
        val credentialType = signedCredential.type.last()
        val domainCredentialSubject = credentialSubjectCaster.castToCredentialSubject(credentialType, signedCredential.credentialSubject)

        val vcType = when (domainCredentialSubject) {
            is EmailCredentialSubject -> {
                VcType.EMAIL_CREDENTIAL
            }
            else -> {
                VcType.UNKNOWN_CREDENTIAL
            }
        }

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
            vcType = vcType,
            expirationDate = expirationDate,
            issuanceDate = issuanceDate,
            holderDid = signedCredential.holder.holderDid,
            issuerDid = signedCredential.issuerDid,
            credentialStatus = credentialStatus,
            credentialSubject = domainCredentialSubject,
            credentialProof = proof
        )
    }

    companion object {
        private const val CREDENTIAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}