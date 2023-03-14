package by.alexandr7035.data.helpers.vc_issuance

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.extensions.getStringDateFromLong
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.repository.AppSettings
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.datasource.cloud.api.CredentialsApiService
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCaster
import by.alexandr7035.data.model.SignedCredential
import by.alexandr7035.data.model.network.credentials.signed_vc.SignVcReq
import by.alexandr7035.data.model.network.credentials.signed_vc.SignVcRes
import by.alexandr7035.data.model.network.credentials.store_vc.StoreVCsReq
import by.alexandr7035.data.model.network.credentials.store_vc.StoreVCsRes
import by.alexandr7035.data.model.network.credentials.unsigned_vc.BuildUnsignedVcReq
import by.alexandr7035.data.model.network.credentials.unsigned_vc.BuildUnsignedVcRes
import by.alexandr7035.data.model.network.credentials.unsigned_vc.UnsignedCredential
import timber.log.Timber
import javax.inject.Inject

class VCIssuanceHelperImpl @Inject constructor(
    private val credentialsApiService: CredentialsApiService,
    private val credentialSubjectCaster: CredentialSubjectCaster,
    private val appSettings: AppSettings
) : VCIssuanceHelper {
    override suspend fun buildUnsignedVC(issueCredentialReqModel: IssueCredentialReqModel): UnsignedCredential {

        val credentialSubject = credentialSubjectCaster.getCredentialSubjectFromCredentialSubjectData(
            credentialContextUrl = issueCredentialReqModel.issuingCredentialData.jsonLdContextUrl,
            credentialSubjectData = issueCredentialReqModel.issuingCredentialData.credentialSubjectData
        )

        Timber.debug("final cred $credentialSubject")

        val request = BuildUnsignedVcReq(
            credentialSubject = credentialSubject,
            expirationDate = issueCredentialReqModel.expiresAt?.getStringDateFromLong(CREDENTIAL_DATE_FORMAT),
            holderDid = issueCredentialReqModel.holderDid,
            jsonLdContextUrl = issueCredentialReqModel.issuingCredentialData.jsonLdContextUrl,
            typeName = issueCredentialReqModel.issuingCredentialData.typeName
        )

        Timber.debug("DEBUG_VC unsigned VC req $request")
        val res = credentialsApiService.buildUnsignedVCObject(request)

        if (res.isSuccessful) {
            // Successfully built unsigned VC
            // Pass it to sign
            val data = res.body() as BuildUnsignedVcRes
            return data.unsignedCredential
        } else {
            throw AppError(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun signCredential(unsignedCredential: UnsignedCredential): SignedCredential {
        val res =
            credentialsApiService.signVC(
                SignVcReq(unsignedCredential = unsignedCredential),
                accessToken = appSettings.getAuthCredentials().accessToken
            )

        if (res.isSuccessful) {
            // Successfully signed VC
            // Pass to store in wallet
            val data = res.body() as SignVcRes
            return data.signedCredential
        } else {
            throw AppError(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun storeCredentials(signedCredentials: List<SignedCredential>): List<String> {
        val res = credentialsApiService.storeVCs(
            body = StoreVCsReq(
                credentialsToStore = signedCredentials,
            ),
            accessToken = appSettings.getAuthCredentials().accessToken
        )

        if (res.isSuccessful) {
            val data = res.body() as StoreVCsRes
            return data.storedVCIDs
        } else {
            // FIXME
            throw AppError(ErrorType.UNKNOWN_ERROR)
        }
    }

    companion object {
        private const val CREDENTIAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}