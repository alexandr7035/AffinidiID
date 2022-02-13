package by.alexandr7035.data.datasource.cloud

import by.alexandr7035.affinidi_id.domain.core.ErrorType

sealed class ApiCallWrapper<T> {
    data class Success<T>(val data: T): ApiCallWrapper<T>()

    data class Fail<T>(val errorType: ErrorType): ApiCallWrapper<T>()

    data class HttpError<T>(val resultCode: Int): ApiCallWrapper<T>()
}