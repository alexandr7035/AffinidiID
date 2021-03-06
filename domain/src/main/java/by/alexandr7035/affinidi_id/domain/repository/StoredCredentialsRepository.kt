package by.alexandr7035.affinidi_id.domain.repository

import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.check_if_have_vc.CheckIfHaveVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.delete_vc.DeleteVcResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.share.ShareCredentialResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import kotlinx.coroutines.flow.Flow

interface StoredCredentialsRepository {
    suspend fun getAllCredentials(authState: AuthStateModel): Flow<CredentialsListResModel>

    suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel, authState: AuthStateModel): DeleteVcResModel

    suspend fun getCredentialById(getCredentialByIdReqModel: GetCredentialByIdReqModel, authState: AuthStateModel): Flow<GetCredentialByIdResModel>

    suspend fun shareCredential(shareVcReq: ShareCredentialReqModel, authState: AuthStateModel): ShareCredentialResModel

    suspend fun checkIfHaveCredentialInCache(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel
}