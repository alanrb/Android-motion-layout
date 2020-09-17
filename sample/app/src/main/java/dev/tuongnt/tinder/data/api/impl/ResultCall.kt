package dev.tuongnt.tinder.data.api.impl

import dev.tuongnt.tinder.data.api.NetworkFailureDispatcher
import dev.tuongnt.tinder.domain.Result
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultCall<T>(
    proxy: Call<T>,
    private val failureDispatcher: NetworkFailureDispatcher
) : CallDelegate<T, Result<T>>(proxy) {

    override fun enqueueImpl(callback: Callback<Result<T>>) = proxy.enqueue(object : Callback<T> {

        override fun onResponse(call: Call<T>, response: Response<T>) {
            val code = response.code()
            val result = if (code in 200 until 300) {
                val body = response.body()
                Result.Success(body)
            } else {
                failureDispatcher.failureFromCode(code, response.errorBody()?.string())
            }

            callback.onResponse(this@ResultCall, Response.success(result))
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            val result = failureDispatcher.failureFromThrowable(t)
            callback.onResponse(this@ResultCall, Response.success(result))
        }
    })

    override fun cloneImpl() = ResultCall(proxy.clone(), failureDispatcher)

    override fun timeout(): Timeout = Timeout.NONE
}
