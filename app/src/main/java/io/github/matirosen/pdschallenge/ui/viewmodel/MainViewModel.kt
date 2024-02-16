package io.github.matirosen.pdschallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.api.WorldClockApiInterface
import io.github.matirosen.pdschallenge.domain.FactorialCalculator
import io.github.matirosen.pdschallenge.exception.InvalidClockRequestException
import io.github.matirosen.pdschallenge.exception.InvalidFactorialInputException
import io.github.matirosen.pdschallenge.model.WorldClockModel
import io.github.matirosen.pdschallenge.repository.WorldClockRepository
import io.github.matirosen.pdschallenge.ui.UiText
import io.github.matirosen.pdschallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO Aca vamos a usar una mutable list para el punto 3, que la recibe el recycler
@HiltViewModel
class MainViewModel @Inject constructor(
    private val factorialCalculator: FactorialCalculator,
    private val worldClockRepository: WorldClockRepository
) : ViewModel() {

    private val _factorialResult = MutableLiveData<Resource<String>>()
    val factorialResult: LiveData<Resource<String>> get() = _factorialResult

    private val _currentClockResult = MutableLiveData<Resource<WorldClockModel>>()
    val currentClockResult: LiveData<Resource<WorldClockModel>> get() = _currentClockResult


    fun calculateFactorialAsync(numberString: String) {
        _factorialResult.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = factorialCalculator.calculateFactorial(numberString)
                _factorialResult.value = Resource.Success(result)
            } catch (e: InvalidFactorialInputException) {
                _factorialResult.value = Resource.Error(e.getUiText())
            } catch (e: Exception) {
                handleUnknownException(e.message, _factorialResult)
            }
        }
    }

    fun getCurrentClock() {
        viewModelScope.launch {
            try {
                _currentClockResult.value = Resource.Loading()
                val result = worldClockRepository.getCurrentClock()
                _currentClockResult.value = Resource.Success(result)
            } catch (e: InvalidClockRequestException){
                _currentClockResult.value = Resource.Error(e.getUiText())
            } catch (e: Exception) {
                handleUnknownException(e.message, _currentClockResult)
            }
        }
    }

    private fun <T> handleUnknownException(message : String?, result : MutableLiveData<Resource<T>>){
        val unknownErrorMessage = UiText.StringResource(R.string.unknown_error)
        if (message != null) {
            result.value = Resource.Error(UiText.DynamicString(message))
        } else {
            result.value = Resource.Error(unknownErrorMessage)
        }
    }

}