package by.alexandr7035.data.helpers.vc_mapping

import by.alexandr7035.affinidi_id.domain.core.extensions.getUnixDateFromStringFormat
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialProof
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialStatus
import by.alexandr7035.data.model.SignedCredential
import com.google.gson.Gson
import com.google.gson.JsonObject
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
                CredentialStatus.Expired
            } else {
                CredentialStatus.Active
            }
        } else {
            CredentialStatus.Active
        }

        val credentialSubjectString = gson.toJson(signedCredential.credentialSubject.data, JsonObject::class.java)

        // Prepare raw VC
        val rawVc = gson.toJson(signedCredential, SignedCredential::class.java)

        // Formatted proof creation date
        val formattedProofDate = signedCredential.proof.creationDate
            .getUnixDateFromStringFormat(CREDENTIAL_PROOF_DATE_FORMAT)

        // Domain credential proof
        val proof = CredentialProof(
            type = signedCredential.proof.type,
            creationDate = formattedProofDate,
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
        private const val CREDENTIAL_PROOF_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }
}