package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject

import by.alexandr7035.affinidi_id.core.extensions.debug
import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDataItem
import com.google.gson.JsonObject
import timber.log.Timber

class CredentialSubjectToFieldsMapperImpl: CredentialSubjectToFieldsMapper {
    override fun map(jsonObject: JsonObject, offsetLevel: Int): List<CredentialDataItem> {
        val fields = ArrayList<CredentialDataItem>()

        val keys = jsonObject.keySet()

        keys.forEach { key ->
           val value = jsonObject.get(key)

            // Primitive (string, int, boolean, etc.)
            when {
                value.isJsonPrimitive -> {
                    fields.add(CredentialDataItem.Field(name = key, value = value.asString, offsetLevel = offsetLevel))
                }
                // Internal json object
                value.isJsonObject -> {
                    // Add field name
                    fields.add(CredentialDataItem.TitleOnly(name = key, offsetLevel = offsetLevel))

                    // Increase offset level (to add margin)
                    val increasedOffset = offsetLevel + 1

                    // Recursion
                    val nestedFields = map(value.asJsonObject, offsetLevel = increasedOffset)
                    fields.addAll(nestedFields)
                }
                // JsonArray / Null values
                // Just stringify, todo find a better solution
                else -> {
//                    fields.add(CredentialDataItem.Field(name = key, value = value.asString))
                }
            }
        }

        Timber.debug("DEBUG_JSON $fields")

        fields.forEach {
            Timber.debug("DEBUG_JSON_OFFSET ${it.offsetLevel}")
        }

        return fields
    }
}