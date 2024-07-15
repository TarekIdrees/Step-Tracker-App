package com.tareq.core.domain.util

typealias GeneralError = Error
typealias EmptyDataResult<GeneralError> = Result<Unit, GeneralError>

sealed interface Result<out D, out E : GeneralError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : GeneralError>(val error: E) : Result<Nothing, E>
}

inline fun <T, E : GeneralError, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

fun <T, E : GeneralError> Result<T, E>.asEmptyDataResult(): EmptyDataResult<E> {
    return map {}
}