package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject

import by.alexandr7035.affinidi_id.presentation.common.credentials.CredentialDataItem
import com.google.gson.JsonObject
import com.google.gson.JsonPrimitive
import org.junit.Assert.assertEquals
import org.junit.Test

class CredentialSubjectToFieldsMapperImplTest {

    private val mapper = CredentialSubjectToFieldsMapperImpl()

    @Test
    fun test_credential_subject_linear() {
        val jsonObject = JsonObject().apply {
            add("name", JsonPrimitive("Name"))
            add("id", JsonPrimitive(12345))
        }

        val uiFieldsObjectActual = mapper.map(jsonObject)

        val uiFieldsObjectExpected = listOf<CredentialDataItem>(
            CredentialDataItem.Field(name = "name", value = "Name", offsetLevel = 0),
            CredentialDataItem.Field(name = "id", value = "12345", offsetLevel = 0)
        )

        assertEquals(uiFieldsObjectExpected, uiFieldsObjectActual)
    }


    @Test
    fun test_credential_subject_with_nested() {

        val nestedJsonObject = JsonObject().apply {
            add("telegramId", JsonPrimitive(12345))
        }

        val jsonObject = JsonObject().apply {
            add("name", JsonPrimitive("Name"))
            add("contacts", nestedJsonObject)
        }

        val uiFieldsObjectActual = mapper.map(jsonObject)

        val uiFieldsObjectExpected = listOf(
            CredentialDataItem.Field(name = "name", value = "Name", offsetLevel = 0),
            CredentialDataItem.TitleOnly(name = "contacts", offsetLevel = 0),
            CredentialDataItem.Field(name = "telegramId", value = "12345", offsetLevel = 1)
        )

        assertEquals(uiFieldsObjectExpected, uiFieldsObjectActual)
    }
}