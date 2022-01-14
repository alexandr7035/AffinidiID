package by.alexandr7035.data.core

import by.alexandr7035.affinidi_id.domain.core.ErrorType
import java.io.IOException

data class AppError(val errorType: ErrorType): IOException()