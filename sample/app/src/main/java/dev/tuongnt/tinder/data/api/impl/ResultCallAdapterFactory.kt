package dev.tuongnt.tinder.data.api.impl

import dev.tuongnt.tinder.data.api.NetworkFailureDispatcher
import dev.tuongnt.tinder.domain.Result
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResultCallAdapterFactory(private val failureDispatcher: NetworkFailureDispatcher) : CallAdapter.Factory() {

    override fun get(returnType: Type, annotations: Array<Annotation>, retrofit: Retrofit) =
        when (getRawType(returnType)) {
            Call::class.java -> {
                val callType = getParameterUpperBound(0, returnType as ParameterizedType)
                when (getRawType(callType)) {
                    Result::class.java -> {
                        val resultType = getParameterUpperBound(0, callType as ParameterizedType)
                        ResultAdapter<Any>(resultType, failureDispatcher)
                    }
                    else -> null
                }
            }
            else -> null
        }
}