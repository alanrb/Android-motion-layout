package dev.tuongnt.tinder.domain

sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<T>(val data: T?) : Result<T>()
    open class Failure : Result<Nothing>()
}

fun <T, V> Result<T>.map(mapper: (T) -> V): Result<V> = when (this) {
    is Result.Success -> if (data != null) {
        Result.Success(mapper.invoke(data))
    } else {
        Result.Success(null)
    }
    else -> this as Result.Failure
}