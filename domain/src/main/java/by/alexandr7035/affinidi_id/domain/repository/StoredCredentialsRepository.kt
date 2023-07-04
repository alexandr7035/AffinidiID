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
import kotlinx.coroutines.flow.Flow

interface StoredCredentialsRepository {
    suspend fun getAllCredentials(): Flow<CredentialsListResModel>

    suspend fun deleteCredential(deleteVcReqModel: DeleteVcReqModel): DeleteVcResModel

    suspend fun getCredentialById(getCredentialByIdReqModel: GetCredentialByIdReqModel): Flow<GetCredentialByIdResModel>

    suspend fun shareCredential(shareVcReq: ShareCredentialReqModel): ShareCredentialResModel

    suspend fun checkIfHaveCredentialInCache(checkIfHaveVcReqModel: CheckIfHaveVcReqModel): CheckIfHaveVcResModel
}