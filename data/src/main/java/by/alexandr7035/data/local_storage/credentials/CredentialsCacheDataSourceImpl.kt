package by.alexandr7035.data.local_storage.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import javax.inject.Inject

class CredentialsCacheDataSourceImpl @Inject constructor(private val credentialsDAO: CredentialsDAO): CredentialsCacheDataSource {
    override suspend fun getCredentialsFromCache(): CredentialsListResModel {
        // TODO FIXME
        return CredentialsListResModel.Success(credentials = emptyList())
    }
}