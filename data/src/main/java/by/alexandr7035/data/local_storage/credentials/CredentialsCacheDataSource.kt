package by.alexandr7035.data.local_storage.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel

interface CredentialsCacheDataSource {
    suspend fun getCredentialsFromCache(): CredentialsListResModel
}