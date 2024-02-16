package io.github.matirosen.pdschallenge.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.databinding.ActivityMainBinding
import io.github.matirosen.pdschallenge.ui.viewmodels.MainViewModel
import io.github.matirosen.pdschallenge.utils.Resource

@AndroidEntryPoint
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
            viewModel.calculateFactorialAsync(numberString)
        }

        viewModel.factorialResult.observe(this) { result ->
            when(result) {
                is Resource.Error -> {
                    Toast.makeText(this, result.message?.asString(this), Toast.LENGTH_SHORT).show()
                    handleFactorialUIState(false)
                }
                is Resource.Success -> {
                    binding.textViewMainActivityFactorialResult.text = result.data
                    handleFactorialUIState(false)
                }
                is Resource.Loading -> {
                    handleFactorialUIState(true)
                }
            }
        }
    }

    private fun handleFactorialUIState(isLoading: Boolean) {
        binding.progressBarMainActivityFactorialResult.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.textViewMainActivityFactorialResult.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.editTextMainActivityWriteNumber.isEnabled = !isLoading
        binding.buttonCalculateFactorial.isEnabled = !isLoading
    }

}