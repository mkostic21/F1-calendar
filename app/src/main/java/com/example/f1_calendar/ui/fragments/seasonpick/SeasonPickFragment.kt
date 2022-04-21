package com.example.f1_calendar.ui.fragments.seasonpick

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.f1_calendar.R
import com.example.f1_calendar.dagger.viewmodel.ViewModelFactory
import com.example.f1_calendar.databinding.FragmentSeasonPickBinding
import javax.inject.Inject

class SeasonPickFragment : Fragment(R.layout.fragment_season_pick) {
    // todo: refactor binding as we talked before
    private lateinit var binding: FragmentSeasonPickBinding

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: SeasonPickerViewModel by activityViewModels { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSeasonPickBinding.bind(view)

        setupNumberPicker()

        // todo: move to separate method
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(this){
            viewModel.setSeason(binding.numberPickerSeason.value.toString())
            isEnabled = false
            dispatcher.onBackPressed()
        }
    }

    // todo: number picker initialization is responsibility of ViewModel (in other words, min max need
    //  to be part of ui state)
    private fun setupNumberPicker() {
        // todo: use run
        binding.apply {
            numberPickerSeason.minValue = 1950
            numberPickerSeason.maxValue = 2022
            numberPickerSeason.wrapSelectorWheel = false
            numberPickerSeason.value = viewModel.season.value!!.toInt()
        }
    }


}