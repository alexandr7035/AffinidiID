package by.alexandr7035.affinidi_id.presentation.common.credentials

sealed class CredentialDataItem {

    // Determines left margin in recyclerview item
    // Use it for inner fields (e.g. for internal jsonObject in json)
    open val offsetLevel: Int = 0

    data class Field(
        val name: String,
        val value: String,
        override val offsetLevel: Int = 0
    ): CredentialDataItem()

    data class TitleOnly(
        val name: String,
        override val offsetLevel: Int = 0
    ): CredentialDataItem()

}
