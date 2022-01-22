package by.alexandr7035.data.local_storage.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.data.model.DataCredentialsList

interface CredentialsCacheDataSource {
    suspend fun getCredentialsFromCache(): DataCredentialsList

    suspend fun saveCredentialsToCache(credentials: DataCredentialsList)

    suspend fun clearCredentialsCache()
}