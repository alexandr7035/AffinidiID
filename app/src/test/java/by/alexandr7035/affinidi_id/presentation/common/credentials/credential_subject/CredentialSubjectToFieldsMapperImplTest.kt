package by.alexandr7035.affinidi_id.presentation.common.credentials.credential_subject

import by.alexandr7035.affinidi_id.presentation.credential_details.model.CredentialFieldUi
import com.google.gson.JsonArray
import com.google.gson.JsonNull
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

        val uiFieldsObjectExpected = listOf<CredentialFieldUi>(
            CredentialFieldUi.Field(name = "name", value = "Name", offsetLevel = 0),
            CredentialFieldUi.Field(name = "id", value = "12345", offsetLevel = 0)
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
            CredentialFieldUi.Field(name = "name", value = "Name", offsetLevel = 0),
            CredentialFieldUi.TitleOnly(name = "contacts", offsetLevel = 0),
            CredentialFieldUi.Field(name = "telegramId", value = "12345", offsetLevel = 1)
        )

        assertEquals(uiFieldsObjectExpected, uiFieldsObjectActual)
    }


    // Case is unlikely to happen but need to handle properly
    @Test
    fun test_credential_subject_with_json_array() {
        val arrayObject = JsonArray().apply {
            add("element1")
            add("element2")
        }

        val jsonObject = JsonObject().apply {
            add("name", JsonPrimitive("Name"))
            add("array", arrayObject)
        }

        val uiFieldsObjectActual = mapper.map(jsonObject)

        val uiFieldsObjectExpected = listOf(
            CredentialFieldUi.Field(name = "name", value = "Name", offsetLevel = 0),
            CredentialFieldUi.Field(name = "array", value = "[\"element1\",\"element2\"]", offsetLevel = 0),
        )

        assertEquals(uiFieldsObjectExpected, uiFieldsObjectActual)
    }


    // Case is unlikely to happen but need to handle properly
    @Test
    fun test_credential_subject_with_nulls() {

        val jsonObject = JsonObject().apply {
            add("name", JsonNull.INSTANCE)
        }

        val uiFieldsObjectActual = mapper.map(jsonObject)

        val uiFieldsObjectExpected = listOf(
            CredentialFieldUi.Field(name = "name", value = "null", offsetLevel = 0),
        )

        assertEquals(uiFieldsObjectExpected, uiFieldsObjectActual)
    }
}