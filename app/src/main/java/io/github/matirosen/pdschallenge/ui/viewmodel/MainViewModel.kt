package io.github.matirosen.pdschallenge.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.domain.FactorialCalculator
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity
import io.github.matirosen.pdschallenge.exception.CustomException
import io.github.matirosen.pdschallenge.model.WorldClockModel
import io.github.matirosen.pdschallenge.repository.LifecycleEventRepository
import io.github.matirosen.pdschallenge.repository.WorldClockRepository
import io.github.matirosen.pdschallenge.ui.UiText
import io.github.matirosen.pdschallenge.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val factorialCalculator: FactorialCalculator,
    private val worldClockRepository: WorldClockRepository,
    private val lifecycleEventRepository: LifecycleEventRepository
) : ViewModel() {

    private val _factorialResult = MutableLiveData<Resource<String>>()
    val factorialResult: LiveData<Resource<String>> get() = _factorialResult

    private val _currentClockResult = MutableLiveData<Resource<WorldClockModel>>()
    val currentClockResult: LiveData<Resource<WorldClockModel>> get() = _currentClockResult

    private val _lifecycleEvents = MutableLiveData<Resource<List<LifecycleEventEntity>>>()
    val lifecycleEvents: LiveData<Resource<List<LifecycleEventEntity>>> get() = _lifecycleEvents

    fun calculateFactorialAsync(numberString: String) {
        viewModelScope.launch {
            performOperation(
                operation =  { factorialCalculator.calculateFactorial(numberString) }  ,
                result = _factorialResult
            )
        }
    }

    fun getCurrentClock() {
        viewModelScope.launch {
            performOperation(
                operation = { worldClockRepository.getCurrentClock() },
                result = _currentClockResult
            )
        }
    }

    fun insertLifecycleEvent(event: LifecycleEventEntity) {
        viewModelScope.launch {
           try{
                lifecycleEventRepository.insert(event)
                getAllLifecycleEvents()
           } catch (e: CustomException) {
                _lifecycleEvents.value = Resource.Error(e.getUiText())
              } catch (e: Exception) {
                handleUnknownException(e.message, _lifecycleEvents)
           }
        }
    }

    fun getAllLifecycleEvents() {
        viewModelScope.launch {
            performOperation(
                operation = { lifecycleEventRepository.getAll() },
                result = _lifecycleEvents
            )
        }
    }

    private suspend fun <T> performOperation(
        operation: suspend () -> T,
        result: MutableLiveData<Resource<T>>
    ) {
        result.value = Resource.Loading()

        try {
            val operationResult = operation()
            result.value = Resource.Success(operationResult)
        } catch (e: CustomException) {
            result.value = Resource.Error(e.getUiText())
        } catch (e: Exception) {
            handleUnknownException(e.message, result)
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