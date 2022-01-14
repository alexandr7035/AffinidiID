package by.alexandr7035.affinidi_id.presentation.helpers.validation

import org.junit.Assert.assertEquals
import org.junit.Test

class InputValidationHelperImplTest {
    private val validator = InputValidationHelperImpl(minPasswordLength = 8)

    @Test
    fun test_password_validation() {

        assertEquals(
            InputValidationResult.WRONG_FORMAT,
            validator.validatePassword("1Ab"),
        )

        assertEquals(
            InputValidationResult.WRONG_FORMAT,
            validator.validatePassword("12345678"),
        )

        assertEquals(
            InputValidationResult.WRONG_FORMAT,
            validator.validatePassword("1234567A"),
        )

        assertEquals(
            InputValidationResult.WRONG_FORMAT,
            validator.validatePassword("1234567a"),
        )

        assertEquals(
            InputValidationResult.NO_ERRORS,
            validator.validatePassword("123456Ab"),
        )
    }

}