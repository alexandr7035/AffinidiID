package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject

import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDataItem
import com.google.gson.JsonObject


// See also unit tests which also may explain clearly how VC data is converted to ui models
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
                value.isJsonArray -> {
                    // JsonArray.asString raises exception when contains more than 1 element
                    // So use toString()
                    fields.add(CredentialDataItem.Field(name = key, value = value.toString()))
                }
                // Null values and other unexpected cases. Just stringify
                value.isJsonNull -> {
                    fields.add(CredentialDataItem.Field(name = key, value = value.toString()))
                }
            }
        }

        return fields
    }
}