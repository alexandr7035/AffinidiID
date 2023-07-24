package by.alexandr7035.affinidi_id.domain.model.credentials.stored_credentials

sealed class CredentialStatus {
    object Active: CredentialStatus()
    object Expired: CredentialStatus()
}