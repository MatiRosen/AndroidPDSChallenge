package io.github.matirosen.pdschallenge.api

import io.github.matirosen.pdschallenge.model.WorldClockModel
import retrofit2.Response
import retrofit2.http.GET

interface WorldClockApiInterface {

    @GET("api/Time/current/zone?timeZone=America/Buenos_Aires")
    suspend fun getCurrentClock(): Response<WorldClockModel>
}