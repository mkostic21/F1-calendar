package com.example.f1_calendar

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.f1_calendar.api.RetrofitInstance
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    private var compositeDisposable: CompositeDisposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()
        loadData()
    }

    private fun loadData() {
        val retrofit = RetrofitInstance.api
        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .subscribe{ response ->
                    Log.d("Response", response.MRData.RaceTable.season)
                    Log.d("Response", response.MRData.RaceTable.Races.toString())
                }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}