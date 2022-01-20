package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.issue_vc.IssueCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import kotlinx.coroutines.flow.Flow

interface CredentialsRepository {
    suspend fun getAllCredentials(authState: AuthStateModel): Flow<CredentialsListResModel>

    suspend fun issueCredential(issueCredentialReqModel: IssueCredentialReqModel, authState: AuthStateModel): IssueCredentialResModel

    suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel, authState: AuthStateModel): DeleteVcResModel
}