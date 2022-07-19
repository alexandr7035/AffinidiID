package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject

import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi
import com.google.gson.JsonObject

interface CredentialSubjectToFieldsMapper {
    fun map(jsonObject: JsonObject, offsetLevel: Int = 0): List<CredentialFieldUi>
}