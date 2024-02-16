package io.github.matirosen.pdschallenge.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.databinding.ActivityMainBinding
import io.github.matirosen.pdschallenge.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupVariables()
        setupListeners()
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()

    }

    override fun onStop() {
        super.onStop()

    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onRestart() {
        super.onRestart()

    }

    private fun setupVariables() {
        binding.progressBarMainActivityFactorialResult.visibility = View.GONE
    }

    private fun setupListeners() {
        setupFactorialFeature()
    }

    private fun setupFactorialFeature() {
        binding.buttonCalculateFactorial.setOnClickListener {
            val numberString = binding.editTextMainActivityWriteNumber.text.toString()
            if (factorialInputValidation(numberString)) {
                binding.progressBarMainActivityFactorialResult.visibility = View.VISIBLE
                binding.textViewMainActivityFactorialResult.visibility = View.GONE
                binding.editTextMainActivityWriteNumber.isEnabled = false
                viewModel.calculateFactorialAsync(numberString.toLong())
            }
        }

        viewModel.factorialResult.observe(this) { result ->
            binding.progressBarMainActivityFactorialResult.visibility = View.GONE
            binding.textViewMainActivityFactorialResult.visibility = View.VISIBLE
            binding.editTextMainActivityWriteNumber.isEnabled = true
            binding.textViewMainActivityFactorialResult.text = getString(R.string.mainActivityFactorialResult, result)
        }

        viewModel.error.observe(this) { error ->
            binding.progressBarMainActivityFactorialResult.visibility = View.GONE
            binding.textViewMainActivityFactorialResult.visibility = View.VISIBLE
            binding.editTextMainActivityWriteNumber.isEnabled = true
            binding.textViewMainActivityFactorialResult.text = error
        }
    }

    private fun factorialInputValidation(numberString: String): Boolean {
        if (numberString.isEmpty()) {
            Toast.makeText(this, getString(R.string.mainActivityFactorialErrorNumberMissing), Toast.LENGTH_SHORT).show()
            return false
        }

        val number = numberString.toLong()
        if (number < 1) {
            binding.textViewMainActivityFactorialResult.text = getString(R.string.mainActivityFactorialErrorMinorOrEqualTo0)
            return false
        } else if (number > 500) {
            binding.textViewMainActivityFactorialResult.text = getString(R.string.mainActivityFactorialResultErrorNumberTooBig)
            return false
        }

        return true
    }

}