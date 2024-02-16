package io.github.matirosen.pdschallenge.service

import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.api.WorldClockApiInterface
import io.github.matirosen.pdschallenge.exception.InvalidClockRequestException
import io.github.matirosen.pdschallenge.model.WorldClockModel
import io.github.matirosen.pdschallenge.ui.UiText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WorldClockService @Inject constructor(
    private val worldClockApiInterface: WorldClockApiInterface
){

    suspend fun getCurrentClock(): WorldClockModel {
        return withContext(Dispatchers.IO){
            val response = worldClockApiInterface.getCurrentClock()
            response.body() ?: throw InvalidClockRequestException(UiText.StringResource(R.string.error_fetching_time))
        }
    }
}