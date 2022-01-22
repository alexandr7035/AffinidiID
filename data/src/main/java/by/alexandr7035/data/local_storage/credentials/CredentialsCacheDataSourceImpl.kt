package by.alexandr7035.data.local_storage.credentials

import by.alexandr7035.data.model.DataCredentialsList
import by.alexandr7035.data.model.credentials.signed_vc.SignedCredential
import com.google.gson.Gson
import javax.inject.Inject

class CredentialsCacheDataSourceImpl @Inject constructor(
    private val credentialsDAO: CredentialsDAO,
    private val gson: Gson
) : CredentialsCacheDataSource {
    override suspend fun getCredentialsFromCache(): DataCredentialsList {

//        val cachedCredentials = credentialsDAO.getCredentials().map {
//            // Return domain
//            Credential(
//                id = it.credentialId,
//                vcType = it.vcType,
//                holderDid = it.holderDid,
//                issuerDid = it.issuerDid,
//                // FIXME
//                credentialSubject = EmailCredentialSubject("fixme@emil.com"),
//                issuanceDate = it.issuanceDate,
//                expirationDate = it.expirationDate,
//                credentialStatus = it.credentialStatus
//            )
//        }

        return DataCredentialsList.Success(signedCredentials = emptyList())
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