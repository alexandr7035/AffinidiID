package by.alexandr7035.data.datasource.cache.credentials

import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.DataGetCredentialById
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
            mapRawVCtoSignedVc(dbVc.rawVc)
        }

        return DataCredentialsList.Success(signedCredentials = signedCredentials)
    }

    override suspend fun getCredentialByIdFromCache(credentialId: String): DataGetCredentialById {
        val dbVc = credentialsDAO.getCredentialById(credentialId)
        val signedCredential = mapRawVCtoSignedVc(dbVc.rawVc)
        return DataGetCredentialById.Success(credential = signedCredential)
    }

    override suspend fun saveCredentialsToCache(credentials: DataCredentialsList) {

        if (credentials is DataCredentialsList.Success) {
            // Save raw JSONs to DB
            val rawVCs = credentials.signedCredentials.map { signedVc ->
                val json = mapSignedVcToRaw(signedVc)
                CredentialEntity(rawVc = json, credentialId = signedVc.id)
            }

            credentialsDAO.saveCredentials(rawVCs)
        }
    }

    override suspend fun clearCredentialsCache() {
        credentialsDAO.deleteCredentials()
    }

    private fun mapRawVCtoSignedVc(rawVc: String): SignedCredential {
        return gson.fromJson(rawVc, SignedCredential::class.java)
    }

    private fun mapSignedVcToRaw(signedCredential: SignedCredential): String {
        return gson.toJson(signedCredential, SignedCredential::class.java)
    }
}