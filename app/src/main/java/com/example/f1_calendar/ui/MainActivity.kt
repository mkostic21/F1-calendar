package com.example.f1_calendar.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
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

    }

}