package io.github.matirosen.pdschallenge.domain

import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.exceptions.InvalidFactorialInputException
import io.github.matirosen.pdschallenge.ui.UiText
import java.math.BigDecimal
import java.math.BigInteger
import java.text.DecimalFormat
import javax.inject.Inject

class FactorialCalculator @Inject constructor() {
    private val scientificNotationLimit = 15
    fun calculateFactorial(numberString: String): String {
        factorialInputValidation(numberString)

        var factorial = BigInteger.ONE
        for (i in 1..numberString.toLong()) {
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

    private fun factorialInputValidation(numberString: String) {
        if (numberString.isEmpty())
            throw InvalidFactorialInputException(UiText.StringResource(R.string.mainActivityFactorialErrorNumberMissing))

        val number: Long
        try {
            number = numberString.toLong()
        } catch (e: NumberFormatException) {
            throw InvalidFactorialInputException(UiText.StringResource(R.string.mainActivityFactoriaErrorNotANumber))
        }

        if (number < 1) {
            throw InvalidFactorialInputException(UiText.StringResource(R.string.mainActivityFactorialErrorMinorOrEqualTo0))
        } else if (number > 5000) {
            throw InvalidFactorialInputException(UiText.StringResource(R.string.mainActivityFactorialErrorNumberTooBig))
        }
    }
}