package io.github.matirosen.pdschallenge.repository

import io.github.matirosen.pdschallenge.service.WorldClockService
import javax.inject.Inject

class WorldClockRepository @Inject constructor(
    private val worldClockService : WorldClockService
){
    suspend fun getCurrentClock() = worldClockService.getCurrentClock()
}