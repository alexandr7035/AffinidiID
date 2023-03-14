package by.alexandr7035.data.helpers.vc_issuance

import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.model.SignedCredential
import by.alexandr7035.data.model.network.credentials.unsigned_vc.UnsignedCredential

interface VCIssuanceHelper {
    suspend fun buildUnsignedVC(issueCredentialReqModel: IssueCredentialReqModel): UnsignedCredential

    suspend fun signCredential(unsignedCredential: UnsignedCredential): SignedCredential

    suspend fun storeCredentials(signedCredentials: List<SignedCredential>): List<String>
}