package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ApiCallWrapper {
    data class Success<T>(val data: T): ApiCallWrapper()

    data class Fail(val errorType: ErrorType): ApiCallWrapper()

    data class HttpError(val resultCode: Int): ApiCallWrapper()
}