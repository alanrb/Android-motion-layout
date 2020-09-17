package dev.tuongnt.tinder.data.api.impl

import dev.tuongnt.tinder.data.api.NetworkFailureDispatcher
import retrofit2.Call
import retrofit2.CallAdapter
import dev.tuongnt.tinder.domain.Result
import java.lang.reflect.Type

class ResultAdapter<T>(
    private val responseType: Type,
    private val failureDispatcher: NetworkFailureDispatcher
) : CallAdapter<T, Call<Result<T>>> {

    override fun responseType() = responseType
    override fun adapt(call: Call<T>): Call<Result<T>> = ResultCall(call, failureDispatcher)
}