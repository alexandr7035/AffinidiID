package by.alexandr7035.data.network

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel

interface  CredentialsCloudDataSource {
    suspend fun getCredentialsFromCloud(authState: AuthStateModel): CredentialsListResModel
}