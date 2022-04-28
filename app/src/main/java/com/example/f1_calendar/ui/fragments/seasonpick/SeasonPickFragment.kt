package com.example.f1_calendar.ui.fragments.seasonpick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentSeasonPickBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeasonPickFragment : Fragment(R.layout.fragment_season_pick) {
    private val viewModel: SeasonPickerViewModel by activityViewModels()

    private var _binding: FragmentSeasonPickBinding? = null
    private val binding get() = _binding!!

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
        setupAppBar()
    }

    private fun setupAppBar() {
        binding.seasonPickTopAppBar.setNavigationOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
    }

    private fun setupViewModelObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            setupNumberPicker(
                minValue = state.minValue,
                maxValue = state.maxValue,
                value = state.value
            )
        }
    }

    private fun handleOnBackPress() {
        val dispatcher = activity?.onBackPressedDispatcher
        dispatcher?.addCallback(this) {
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