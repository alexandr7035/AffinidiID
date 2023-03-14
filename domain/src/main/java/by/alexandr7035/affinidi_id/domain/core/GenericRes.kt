package by.alexandr7035.affinidi_id.domain.core

sealed class GenericRes<T> {
    data class Success<T>(val data: T): GenericRes<T>()

    data class Fail<T>(val errorType: ErrorType): GenericRes<T>()
}