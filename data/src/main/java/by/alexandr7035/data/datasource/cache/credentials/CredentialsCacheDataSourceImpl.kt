package by.alexandr7035.data.datasource.cache.credentials

import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.SignedCredential
import by.alexandr7035.data.model.local.credentials.CredentialEntity
import com.google.gson.Gson
import javax.inject.Inject

class CredentialsCacheDataSourceImpl @Inject constructor(
    private val credentialsDAO: CredentialsDAO,
    private val gson: Gson,
) : CredentialsCacheDataSource {
    override suspend fun getCredentialsFromCache(): DataCredentialsList {
        // Map raw to signed credential
        val signedCredentials = credentialsDAO.getCredentials().map { dbVc ->
            gson.fromJson(dbVc.rawVc, SignedCredential::class.java)
        }

        return DataCredentialsList.Success(signedCredentials = signedCredentials)
    }

    override suspend fun saveCredentialsToCache(credentials: DataCredentialsList) {

        if (credentials is DataCredentialsList.Success) {
            // Save raw JSONs to DB
            val rawVCs = credentials.signedCredentials.map { signedVc ->
                val json = gson.toJson(signedVc, SignedCredential::class.java)
                CredentialEntity(rawVc = json)
            }

            credentialsDAO.saveCredentials(rawVCs)
        }
    }

    override suspend fun clearCredentialsCache() {
        credentialsDAO.deleteCredentials()
    }
}