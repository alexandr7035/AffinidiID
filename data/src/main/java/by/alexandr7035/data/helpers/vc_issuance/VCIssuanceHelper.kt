package by.alexandr7035.data.helpers.vc_issuance

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential
import by.alexandr7035.data.model.credentials.unsigned_vc.BuildUnsignedVcRes
import by.alexandr7035.data.model.credentials.unsigned_vc.UnsignedCredential

interface VCIssuanceHelper {
    suspend fun buildUnsignedVC(issueCredentialReqModel: IssueCredentialReqModel): UnsignedCredential

    suspend fun signCredential(unsignedCredential: UnsignedCredential, authState: AuthStateModel): SignedCredential

    suspend fun storeCredentials(signedCredentials: List<SignedCredential>, authState: AuthStateModel): List<String>
}