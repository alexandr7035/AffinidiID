package by.alexandr7035.data.local_storage.credentials

import by.alexandr7035.affinidi_id.domain.model.credentials.common.credential_subject.EmailCredentialSubject
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials.CredentialsListResModel
import by.alexandr7035.data.helpers.vc_mapping.CredentialSubjectCaster
import javax.inject.Inject

class CredentialsCacheDataSourceImpl @Inject constructor(
    private val credentialsDAO: CredentialsDAO,
    private val credentialSubjectCaster: CredentialSubjectCaster,
    ): CredentialsCacheDataSource {
    override suspend fun getCredentialsFromCache(): CredentialsListResModel {

        val cachedCredentials = credentialsDAO.getCredentials().map {
            // Return domain
            Credential(
                id = it.credentialId,
                vcType = it.vcType,
                holderDid = it.holderDid,
                issuerDid = it.issuerDid,
                // FIXME
                credentialSubject = EmailCredentialSubject("fixme@emil.com"),
                issuanceDate = it.issuanceDate,
                expirationDate = it.expirationDate,
                credentialStatus = it.credentialStatus
            )
        }

        return CredentialsListResModel.Success(credentials = cachedCredentials)
    }

    override suspend fun saveCredentialsToCache(credentials: CredentialsListResModel) {
        if (credentials is CredentialsListResModel.Success) {
            val credentialsToSave = credentials.credentials.map {
                CredentialEntity(
                    credentialId = it.id,
                    vcType = it.vcType,
                    holderDid = it.holderDid,
                    issuerDid = it.issuerDid,
                    credentialSubject = credentialSubjectCaster.credentialSubjectToJson(it.credentialSubject),
                    issuanceDate = it.issuanceDate,
                    expirationDate = it.expirationDate,
                    credentialStatus = it.credentialStatus
                )
            }

            credentialsDAO.saveCredentials(credentialsToSave)
        }
    }

    override suspend fun clearCredentialsCache() {
        credentialsDAO.deleteCredentials()
    }
}