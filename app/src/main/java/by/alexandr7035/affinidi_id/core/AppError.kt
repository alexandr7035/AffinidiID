package by.alexandr7035.affinidi_id.core

import java.io.IOException

data class AppError(val errorType: ErrorType): IOException()