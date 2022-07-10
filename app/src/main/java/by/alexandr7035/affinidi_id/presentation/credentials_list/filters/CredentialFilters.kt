package by.alexandr7035.affinidi_id.presentation.credentials_list.filters

sealed class CredentialFilters() {
    object All: CredentialFilters()

    object Active: CredentialFilters()

    object Expired: CredentialFilters()
}
