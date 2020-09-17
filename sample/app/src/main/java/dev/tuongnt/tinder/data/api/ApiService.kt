package dev.tuongnt.tinder.data.api

import dev.tuongnt.tinder.data.api.dto.ResultApiDto
import dev.tuongnt.tinder.domain.Result
import retrofit2.http.GET

interface ApiService {

    @GET("0.4/?randomapi")
    suspend fun randomUser(): Result<ResultApiDto>

}