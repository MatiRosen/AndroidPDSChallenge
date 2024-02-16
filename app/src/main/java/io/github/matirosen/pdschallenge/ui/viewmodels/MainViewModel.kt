package io.github.matirosen.pdschallenge.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.domain.FactorialCalculator
import io.github.matirosen.pdschallenge.exceptions.InvalidFactorialInputException
import io.github.matirosen.pdschallenge.ui.UiText
import io.github.matirosen.pdschallenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// TODO Aca vamos a usar una mutable list para el punto 3, que la recibe el recycler
@HiltViewModel
class MainViewModel @Inject constructor(private val factorialCalculator: FactorialCalculator) : ViewModel() {

    private val _factorialResult = MutableLiveData<Resource<String>>()
    val factorialResult: LiveData<Resource<String>> get() = _factorialResult


    fun calculateFactorialAsync(numberString: String) {
        _factorialResult.value = Resource.Loading()

        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) { factorialCalculator.calculateFactorial(numberString) }
                _factorialResult.value = Resource.Success(result)
            } catch (e: InvalidFactorialInputException) {
                _factorialResult.value = Resource.Error(e.getUiText())
            } catch (e: Exception) {
                val unknownErrorMessage = UiText.StringResource(R.string.unknown_error)
                if (e.message != null) {
                    _factorialResult.value = Resource.Error(UiText.DynamicString(e.message!!))
                } else {
                    _factorialResult.value = Resource.Error(unknownErrorMessage)
                }
            }
        }
    }

}