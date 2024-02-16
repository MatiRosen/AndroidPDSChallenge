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
            viewModel.calculateFactorialAsync(numberString)
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

        viewModel.logMessage.observe(this) { message ->
            Toast.makeText(this, message.asString(this), Toast.LENGTH_SHORT).show()
        }
    }
}