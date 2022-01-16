package by.alexandr7035.data.repository

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import by.alexandr7035.affinidi_id.domain.core.extensions.getUnixDateFromStringFormat
import by.alexandr7035.affinidi_id.domain.model.credentials.Credential
import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialStatus
import by.alexandr7035.affinidi_id.domain.model.credentials.CredentialsListResModel
import by.alexandr7035.affinidi_id.domain.model.login.AuthStateModel
import by.alexandr7035.affinidi_id.domain.repository.CredentialsRepository
import by.alexandr7035.data.core.AppError
import by.alexandr7035.data.extensions.debug
import by.alexandr7035.data.model.credentials.CredentialRes
import by.alexandr7035.data.network.CredentialsApiService
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class CredentialsRepositoryImpl @Inject constructor(private val apiService: CredentialsApiService): CredentialsRepository {
    override suspend fun getAllCredentials(authState: AuthStateModel): CredentialsListResModel {

        try {
            val res = apiService.getAllCredentials(authState.accessToken ?: "")

            if (res.isSuccessful) {
                val data = res.body() as List<CredentialRes>

                // TODO mapper
                val domainCreds = data.map {

                    val expirationDate = if (it.expirationDate == null) {
                        null
                    }
                    else {
                        it.expirationDate.getUnixDateFromStringFormat(CREDENTIAL_DATE_FORMAT)
                    }

                    val issuanceDate = it.issuanceDate.getUnixDateFromStringFormat(CREDENTIAL_DATE_FORMAT)

                    val credentialStatus = if (expirationDate != null) {
                        if (expirationDate < System.currentTimeMillis()) {
                            CredentialStatus.EXPIRED
                        } else {
                            CredentialStatus.ACTIVE
                        }
                    }
                    else {
                        CredentialStatus.ACTIVE
                    }

                    Credential(
                        id = it.id,
                        // Fist type will be "VerifiableCredential"
                        credentialType = it.type.last(),
                        expirationDate = expirationDate,
                        issuanceDate = issuanceDate,
                        holderDid = it.holder.holderDid,
                        issuerDid = it.issuerDid,
                        credentialStatus = credentialStatus
                    )
                }

                return CredentialsListResModel.Success(domainCreds)
            }
            else {
                return when (res.code()) {
                    401 -> {
                        CredentialsListResModel.Fail(ErrorType.AUTHORIZATION_ERROR)
                    }
                    else -> {
                        CredentialsListResModel.Fail(ErrorType.UNKNOWN_ERROR)
                    }
                }
            }
        }
        // Handled in ErrorInterceptor
        catch (appError: AppError) {
            return CredentialsListResModel.Fail(appError.errorType)
        }
        // Unknown exception
        catch (e: Exception) {
            e.printStackTrace()
            return CredentialsListResModel.Fail(ErrorType.UNKNOWN_ERROR)
        }
    }

    companion object {
        private const val CREDENTIAL_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    }
}