package by.alexandr7035.affinidi_id.presentation.common.errors

import by.alexandr7035.affinidi_id.domain.core.ErrorType

interface ErrorTypeMapper {
    fun map(errorType: ErrorType): DetailedErrorUi
}