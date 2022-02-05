package by.alexandr7035.affinidi_id.presentation.credential_details.credential_ui.credential_subject

import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataItem
import com.google.gson.JsonObject

interface CredentialSubjectToFieldsMapper {
    fun map(jsonObject: JsonObject, offsetLevel: Int = 0): List<CredentialDataItem>
}