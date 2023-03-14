package by.alexandr7035.affinidi_id.domain.core

sealed class Result<T> {
    data class Success<T>(val data: T): Result<T>()

    data class Fail<T>(val errorType: ErrorType): Result<T>()
}