package by.alexandr7035.affinidi_id.presentation.credential_details

abstract class CredentialDataItem {
    class Field(
        val name: String,
        val value: String
    ): CredentialDataItem()

    class Spacing(): CredentialDataItem()
}
