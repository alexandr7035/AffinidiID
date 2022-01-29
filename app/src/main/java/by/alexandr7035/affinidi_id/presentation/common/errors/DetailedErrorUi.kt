package by.alexandr7035.affinidi_id.presentation.common.errors

import by.alexandr7035.affinidi_id.domain.core.ErrorType

data class DetailedErrorUi(
    val errorType: ErrorType,
    val title: String,
    val message: String
)