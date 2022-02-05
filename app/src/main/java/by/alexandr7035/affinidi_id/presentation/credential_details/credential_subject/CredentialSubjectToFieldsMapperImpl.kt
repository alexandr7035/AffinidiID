package by.alexandr7035.affinidi_id.presentation.credential_details.credential_subject

import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.presentation.credential_details.CredentialDataItem
import com.google.gson.JsonObject
import timber.log.Timber

class CredentialSubjectToFieldsMapperImpl: CredentialSubjectToFieldsMapper {
    override fun map(jsonObject: JsonObject): List<CredentialDataItem> {

        val fields = ArrayList<CredentialDataItem>()

        val keys = jsonObject.keySet()
        Timber.debug("DEBUG_JSON $keys")

        keys.forEach { key ->
           val value = jsonObject.get(key)
           fields.add(CredentialDataItem.Field(name = key, value = value.toString()))
        }

        Timber.debug("DEBUG_JSON $fields")
        return fields
    }
}