package dev.tuongnt.tinder.data.api

import com.google.common.truth.Truth.assertThat
import dev.tuongnt.tinder.UnitTest
import dev.tuongnt.tinder.data.api.dto.UserResultApiDto
import dev.tuongnt.tinder.data.api.impl.NetworkFailure
import dev.tuongnt.tinder.data.api.impl.ResultCallAdapterFactory
import dev.tuongnt.tinder.domain.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoroutinesApi
class ApiServiceTest : UnitTest() {

    private lateinit var service: ApiService
    private lateinit var mockWebServer: MockWebServer
    private lateinit var failureDispatcher: NetworkFailureDispatcher

    override fun setUp() {
        super.setUp()
        failureDispatcher = NetworkFailure.NetworkFailureDispatcherImpl()
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory(failureDispatcher))
            .build()
            .create(ApiService::class.java)
    }

    override fun tearDown() {
        super.tearDown()
        mockWebServer.shutdown()
    }

    @Test
    fun `Should return result of failure from error code`() {
        runBlocking {
            //Given
            enqueueResponse(code = 404)
            //When
            val response = service.randomUser()
            //Then
            assertThat(response).isInstanceOf(NetworkFailure.NotFound::class.java)
        }
    }

    @Test
    fun `Should return result of user list`() {
        runBlocking {
            //Given
            enqueueResponse("userList.json")

            //When
            val response = service.randomUser()
            val data = (response as Result.Success).data!!

            //Then
            assertThat(data.results.size).isEqualTo(1)
            data.results[0].let {
                assertThat(it).isInstanceOf(UserResultApiDto::class.java)
                assertThat(it.user.email).isEqualTo("avery.wright61@example.com")
            }
        }
    }

    private fun enqueueResponse(
        fileName: String? = null,
        code: Int = 200,
        headers: Map<String, String> = emptyMap()
    ) {
        val mockResponse = MockResponse().apply {
            setResponseCode(code)
            for ((key, value) in headers) {
                addHeader(key, value)
            }
            if (!fileName.isNullOrEmpty()) {
                setBody(
                    javaClass.classLoader!!.getResourceAsStream("api.response/$fileName")
                        .source()
                        .buffer()
                        .readString(Charsets.UTF_8)
                )
            }
        }
        mockWebServer.enqueue(mockResponse)
    }
}