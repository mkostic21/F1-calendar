package com.example.f1_calendar.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.adapter.RaceCalendarRecyclerViewAdapter
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.databinding.ActivityMainBinding
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.model.ui.MainActivityUiState
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   @Inject lateinit var api: F1Api
    private lateinit var viewModel: MainActivityViewModel
    lateinit var binding: ActivityMainBinding

    lateinit var adapter: RaceCalendarRecyclerViewAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as F1Application).f1Component.inject(this)

        val repository = F1ApiRaceTableRepository(api)
        val viewModelProviderFactory = MainActivityViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        setupRecycler()

        viewModel.uiState.observe(this
        ) {
            when(it){
                is MainActivityUiState.Success -> {
                    adapter.submitList(it.listItems)
                    // todo: hide loader
                }
                is MainActivityUiState.Error -> Log.d("Response", it.t.toString()) // todo: hide loader
                is MainActivityUiState.Loading -> Log.d("Response", "Loading...")//todo: Show loading
            }
        }

    }

    private fun setupRecycler() {
        adapter = RaceCalendarRecyclerViewAdapter()
        binding.rvRaceCalendar.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvRaceCalendar.adapter = adapter
    }

}