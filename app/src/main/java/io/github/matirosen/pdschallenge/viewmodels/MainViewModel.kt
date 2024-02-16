package io.github.matirosen.pdschallenge.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat

// TODO Aca vamos a usar una mutable list para el punto 3, que la recibe el recycler
class MainViewModel : ViewModel() {

    private val _factorialResult = MutableLiveData<String>()
    val factorialResult: LiveData<String> get() = _factorialResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val scientificNotationLimit = 15


    fun calculateFactorialAsync(number: Long) {
        viewModelScope.launch {
            try {
                val result = withContext(Dispatchers.Default) { calculateFactorial(number) }
                _factorialResult.value = result
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    private fun calculateFactorial(number: Long): String {
        var factorial = BigInteger.ONE
        for (i in 1..number) {
            factorial = factorial.multiply(BigInteger.valueOf(i))
        }

        val bigDecimalFactorial = BigDecimal(factorial)
        val decimalFormat = DecimalFormat("0.############E0")
        val plainString = bigDecimalFactorial.toPlainString()

        return if (plainString.length > scientificNotationLimit) {
            decimalFormat.format(bigDecimalFactorial)
        } else {
            plainString
        }
    }
}