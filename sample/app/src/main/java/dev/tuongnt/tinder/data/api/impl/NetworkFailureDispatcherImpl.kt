package dev.tuongnt.tinder.data.api.impl

import dev.tuongnt.tinder.data.api.NetworkFailureDispatcher
import dev.tuongnt.tinder.domain.Result
import java.io.IOException
import java.net.SocketTimeoutException

sealed class NetworkFailure() : Result.Failure() {
    object SessionExpired : NetworkFailure()
    object SocketTimeout : NetworkFailure()
    object NotFound : NetworkFailure()
    object GoneError : NetworkFailure()
    object NetworkError : NetworkFailure()
    object UnknownError : NetworkFailure()

    class NetworkFailureDispatcherImpl : NetworkFailureDispatcher {

        override fun failureFromCode(code: Int, body: Any?): NetworkFailure {
            return when (code) {
                401 -> SessionExpired
                404 -> NotFound
                410 -> GoneError
                else -> UnknownError
            }
        }

        override fun failureFromThrowable(throwable: Throwable): Failure {
            return when (throwable) {
                is IOException -> NetworkError
                is SocketTimeoutException -> SocketTimeout
                else -> UnknownError
            }
        }
    }
}