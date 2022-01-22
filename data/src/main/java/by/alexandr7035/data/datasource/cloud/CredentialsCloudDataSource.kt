package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.GetCredentialByIdReqModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.DataGetCredentialById

interface CredentialsCloudDataSource {
    suspend fun getCredentialsFromCloud(authState: AuthStateModel): DataCredentialsList

    suspend fun getCredentialByIdFromCloud(authState: AuthStateModel, getCredentialByIdReq: GetCredentialByIdReqModel): DataGetCredentialById
}