package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.data.model.DataCredentialsList

interface CredentialsCloudDataSource {
    suspend fun getCredentialsFromCloud(): DataCredentialsList
}