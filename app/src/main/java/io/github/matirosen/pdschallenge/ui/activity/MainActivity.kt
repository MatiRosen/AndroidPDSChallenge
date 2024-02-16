package io.github.matirosen.pdschallenge.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.github.matirosen.pdschallenge.R
import io.github.matirosen.pdschallenge.adapter.LifecycleEventAdapter
import io.github.matirosen.pdschallenge.databinding.ActivityMainBinding
import io.github.matirosen.pdschallenge.entity.LifecycleEventEntity
import io.github.matirosen.pdschallenge.ui.viewmodel.MainViewModel
import io.github.matirosen.pdschallenge.utils.Resource

//TODO poner splash e icono
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentToast: Toast? = null

    private val viewModel: MainViewModel by viewModels()
    private lateinit var lifecycleEventAdapter: LifecycleEventAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setupVariables()
        setupListeners()

        createEvent("onCreate")
    }

    override fun onStart() {
        super.onStart()
        createEvent("onStart")
    }

    override fun onResume() {
        super.onResume()
        createEvent("onResume")
    }

    override fun onPause() {
        super.onPause()
        createEvent("onPause")
    }

    override fun onStop() {
        super.onStop()
        createEvent("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        createEvent("onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        createEvent("onRestart")
    }

    private fun setupVariables() {
        setupRecyclerView()
        observeLifecycleEvents()
        viewModel.getAllLifecycleEvents()
        binding.progressBarMainActivityGeneral.visibility = View.GONE
    }

    private fun setupListeners() {
        setupFactorialFeature()
        setupWorldClockFeature()
    }

    private fun setupRecyclerView() {
        lifecycleEventAdapter = LifecycleEventAdapter()
        binding.recyclerViewMainActivityEventRegister.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = lifecycleEventAdapter
        }
    }

    private fun observeLifecycleEvents() {
        viewModel.lifecycleEvents.observe(this) { events ->
            when(events) {
                is Resource.Error -> {
                    showToast(events.message?.asString(this))
                }
                is Resource.Success -> {
                    lifecycleEventAdapter.submitList(events.data)
                }
                is Resource.Loading -> {

                }
            }
        }
    }

    private fun setupFactorialFeature() {
        binding.buttonCalculateFactorial.setOnClickListener {
            val numberString = binding.editTextMainActivityWriteNumber.text.toString()
            viewModel.calculateFactorialAsync(numberString)
        }

        viewModel.factorialResult.observe(this) { result ->
            when(result) {
                is Resource.Error -> {
                    showToast(result.message?.asString(this))
                    handleFactorialUIState(false)
                }
                is Resource.Success -> {
                    val text = getString(R.string.mainActivityFactorialResult, result.data)
                    binding.textViewMainActivityFactorialResult.text = text
                    handleFactorialUIState(false)
                }
                is Resource.Loading -> {
                    handleFactorialUIState(true)
                }
            }
        }
    }

    private fun handleFactorialUIState(isLoading: Boolean) {
        binding.progressBarMainActivityGeneral.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.textViewMainActivityFactorialResult.visibility = if (isLoading) View.GONE else View.VISIBLE
        binding.editTextMainActivityWriteNumber.isEnabled = !isLoading
        binding.buttonCalculateFactorial.isEnabled = !isLoading
    }

    private fun setupWorldClockFeature() {
        binding.buttonConsumeAPI.setOnClickListener {
            viewModel.getCurrentClock()
        }

        viewModel.currentClockResult.observe(this) { result ->
            when(result) {
                is Resource.Error -> {
                    showToast(result.message?.asString(this), Toast.LENGTH_LONG)
                    handleClockUIState(false)
                }
                is Resource.Success -> {
                    showToast(result.data.toString(), Toast.LENGTH_LONG)
                    handleClockUIState(false)
                }
                is Resource.Loading -> {
                    handleClockUIState(true)
                }
            }
        }
    }

    private fun handleClockUIState(isLoading: Boolean) {
        binding.buttonConsumeAPI.isEnabled = !isLoading
    }


    private fun showToast(message: String?, length: Int = Toast.LENGTH_SHORT) {
        if (message == null) return

        currentToast?.cancel()
        currentToast = Toast.makeText(this, message,length).apply {
            show()
        }
    }

    private fun createEvent(eventName: String) {
        val event = LifecycleEventEntity(eventName = eventName, eventTimestamp = System.currentTimeMillis())
        viewModel.insertLifecycleEvent(event)
    }

}