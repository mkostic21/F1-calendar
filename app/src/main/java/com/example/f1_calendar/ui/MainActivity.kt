package com.example.f1_calendar.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.model.ui.MainActivityUiState
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

   @Inject lateinit var api: F1Api
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as F1Application).f1Component.inject(this)

        val repository = F1ApiRaceTableRepository(api)
        val viewModelProviderFactory = MainActivityViewModelProviderFactory(repository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(MainActivityViewModel::class.java)

        viewModel.uiState.observe(this
        ) {
            when(it){
                is MainActivityUiState.Success -> {
                    Log.d("Response", it.listItems[15].toString())
                    Log.d("Response", it.listItems[18].toString())
                    Log.d("Response", it.listItems[20].toString())
                    Log.d("Response", it.listItems[23].toString())
                    // todo: hide loader
                }
                is MainActivityUiState.Error -> Log.d("Response", it.t.toString()) // todo: hide loader
                is MainActivityUiState.Loading -> Log.d("Response", "Loading...")//todo: Show loading
            }
        }

    }

}