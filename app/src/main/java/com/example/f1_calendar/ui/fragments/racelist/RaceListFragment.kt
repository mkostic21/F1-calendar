package com.example.f1_calendar.ui.fragments.racelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.adapter.RaceListRecyclerViewAdapter
import com.example.f1_calendar.databinding.FragmentRaceListBinding
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiState
import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem
import com.example.f1_calendar.ui.fragments.seasonpick.SeasonPickerViewModel
import javax.inject.Inject

class RaceListFragment : Fragment(R.layout.fragment_race_list), OnHeaderItemSelectedListener,
    OnEventItemSelectedListener {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RaceListViewModel by viewModels { viewModelFactory }

    // todo: use SelectedSeasonProvider instead of SeasonPickerViewModel
    // todo: use injected viewModelFactory
    private val pickerViewModel: SeasonPickerViewModel by activityViewModels()

    // TODO: binding
    private lateinit var binding: FragmentRaceListBinding

    private val adapter: RaceListRecyclerViewAdapter by lazy {
        // todo: instantiate listeners here instead of making the fragment inherit from them
        //  Do this by making getOnHeaderItemSelectedListener method and similar
        RaceListRecyclerViewAdapter(this, this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // todo: move to attach
        (activity?.application as F1Application).f1Component.inject(this)
        binding = FragmentRaceListBinding.bind(view)

        setupRecyclerView()

        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is RaceListFragmentUiState.Success -> {
                    adapter.submitList(state.listItems)
                    hideProgressBar()
                }
                is RaceListFragmentUiState.Error -> {
                    Log.d("Response", state.t.toString())
                    hideProgressBar()
                }
                is RaceListFragmentUiState.Loading -> {
                    Log.d("Response", "Loading...")
                    showProgressBar()
                }
            }
        }

        pickerViewModel.season.observe(viewLifecycleOwner){ season ->
            viewModel.fetchUiState(season = season)
        }

        //todo: implement appbar and add button
        binding.fabTest.setOnClickListener {
            val action = RaceListFragmentDirections.actionRaceListFragmentToSeasonPickFragment()
            findNavController().navigate(action)
        }
    }

    private fun showProgressBar() {
        Log.d("Response", "Showing progressbar")
        binding.progressCircular.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        Log.d("Response", "Hiding progressbar")
        binding.progressCircular.visibility = View.GONE

    }

    private fun setupRecyclerView() {
        binding.rvRaceList.adapter = adapter
        binding.rvRaceList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    override fun toggleHeader(header: RaceWeekListItem.Header) {
        viewModel.toggleCollapsedHeader(header = header)
    }

    override fun showDetails(header: RaceWeekListItem.Header) {
        //todo: add animation
        val circuitId = header.circuitId
        val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(circuitId, pickerViewModel.season.value)
        findNavController().navigate(action)
    }

    override fun showDetails(event: RaceWeekListItem.Event) {
        //todo:add animation
        val circuitId = event.circuitId
        val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(circuitId, pickerViewModel.season.value)
        findNavController().navigate(action)
    }
}