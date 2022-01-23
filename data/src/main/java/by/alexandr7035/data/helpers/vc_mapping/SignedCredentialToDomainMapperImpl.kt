package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.core.extensions.getUnixDateFromStringFormat
import by.alexandr7035.affinidi_id.domain.model.credentials.common.VcType
import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubjectData
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.data.model.SignedCredential
import javax.inject.Inject

// Cast credentials received from wallet to available domain types
class SignedCredentialToDomainMapperImpl @Inject constructor(private val credentialSubjectCaster: CredentialSubjectCaster): SignedCredentialToDomainMapper {
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

        // Fist type will ALWAYS be "https://www.w3.org/2018/credentials/v1"
        // according to W3C spec
        // Get last context url to detect VC type
        val credentialContextUrl = signedCredential.context.last()
        val domainCredentialSubjectData = credentialSubjectCaster.castToCredentialSubjectData(credentialContextUrl, signedCredential.credentialSubject)

        val vcType = when (domainCredentialSubjectData) {
            is EmailCredentialSubjectData -> {
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
            credentialSubjectData = domainCredentialSubjectData,
            credentialProof = proof
        )
    }

    companion object {
        private const val CREDENTIAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}