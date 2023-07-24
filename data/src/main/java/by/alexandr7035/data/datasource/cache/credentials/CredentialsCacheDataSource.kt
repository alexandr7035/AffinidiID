package by.alexandr7035.data.datasource.cache.credentials

import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.DataGetCredentialById

interface CredentialsCacheDataSource {
    suspend fun getCredentialsFromCache(): DataCredentialsList

    suspend fun getCredentialByIdFromCache(credentialId: String): DataGetCredentialById

    suspend fun saveCredentialsToCache(credentials: DataCredentialsList)

    suspend fun clearCredentialsCache()
}