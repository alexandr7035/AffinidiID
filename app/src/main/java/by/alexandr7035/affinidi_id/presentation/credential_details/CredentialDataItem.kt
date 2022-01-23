package by.alexandr7035.affinidi_id.presentation.credential_details

import by.alexandr7035.affinidi_id.presentation.credentials_list.CredentialItemUiModel

abstract class CredentialDataItem {
    class Field(
        val name: String,
        val value: String
    ): CredentialDataItem()

    class Spacing(): CredentialDataItem()
}
