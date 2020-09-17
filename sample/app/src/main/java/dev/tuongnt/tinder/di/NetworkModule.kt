package dev.tuongnt.tinder.di

import dev.tuongnt.tinder.BuildConfig
import dev.tuongnt.tinder.data.api.ApiService
import dev.tuongnt.tinder.data.api.NetworkFailureDispatcher
import dev.tuongnt.tinder.data.api.impl.NetworkFailure
import dev.tuongnt.tinder.data.api.impl.ResultCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit

val networkModule = module {

    single<ApiService> { createWebService(get(), get(), BuildConfig.API_URL) }
    single { createOkHttpClient() }
    single<NetworkFailureDispatcher> { NetworkFailure.NetworkFailureDispatcherImpl() }
}


inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    failureDispatcher: NetworkFailureDispatcher,
    url: String
): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(nullOnEmptyConverterFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(ResultCallAdapterFactory(failureDispatcher))
        .build()
        .create(T::class.java)
}

fun createOkHttpClient(): OkHttpClient =
    OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(30L, TimeUnit.SECONDS)
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                else HttpLoggingInterceptor.Level.NONE
            )
        )
        .build()

fun nullOnEmptyConverterFactory() = object : Converter.Factory() {

    fun converterFactory() = this

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ) = object : Converter<ResponseBody, Any?> {
        val nextResponseBodyConverter =
            retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)

        override fun convert(value: ResponseBody) =
            if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
    }
}