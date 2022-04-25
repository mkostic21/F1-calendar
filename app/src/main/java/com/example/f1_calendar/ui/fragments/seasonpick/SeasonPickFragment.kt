package com.example.f1_calendar.ui.fragments.seasonpick

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentSeasonPickBinding
import javax.inject.Inject

class SeasonPickFragment : Fragment(R.layout.fragment_season_pick) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: SeasonPickerViewModel by activityViewModels { viewModelFactory }

    private var _binding: FragmentSeasonPickBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        (activity?.application as F1Application).f1Component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSeasonPickBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUiState()
        setupViewModelObserver()
        handleOnBackPress()
    }

    private fun setupViewModelObserver() {
        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            setupNumberPicker(
                minValue = state.minValue,
                maxValue = state.maxValue,
                value = state.value
            )
        }
    }

    private fun handleOnBackPress() {
        val dispatcher = requireActivity().onBackPressedDispatcher
        dispatcher.addCallback(this){
            viewModel.setSeason(binding.numberPickerSeason.value.toString())
            isEnabled = false
            dispatcher.onBackPressed()
        }
    }

    private fun setupNumberPicker(minValue: Int, maxValue: Int, value: Int) {
        binding.run {
            numberPickerSeason.minValue = minValue
            numberPickerSeason.maxValue = maxValue
            numberPickerSeason.value = value
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}