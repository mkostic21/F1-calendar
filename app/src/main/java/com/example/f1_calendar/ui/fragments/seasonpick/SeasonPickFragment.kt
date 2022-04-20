package com.example.f1_calendar.ui.fragments.seasonpick

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentSeasonPickBinding

class SeasonPickFragment : Fragment(R.layout.fragment_season_pick) {
    private lateinit var binding: FragmentSeasonPickBinding
    private val viewModel: SeasonPickerViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSeasonPickBinding.bind(view)

        setupNumberPicker()

        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(this){
            viewModel.setSeason(binding.numberPickerSeason.value.toString())
            isEnabled = false
            dispatcher.onBackPressed()
        }
    }

    private fun setupNumberPicker() {
        binding.apply {
            numberPickerSeason.minValue = 1950
            numberPickerSeason.maxValue = 2022
            numberPickerSeason.wrapSelectorWheel = false
            numberPickerSeason.value = viewModel.season.value!!.toInt()
        }
    }


}