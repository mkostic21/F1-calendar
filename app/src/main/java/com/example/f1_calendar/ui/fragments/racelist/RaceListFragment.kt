package com.example.f1_calendar.ui.fragments.racelist

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.f1_calendar.ui.fragments.seasonpick.SelectedSeasonProvider
import javax.inject.Inject

class RaceListFragment : Fragment(R.layout.fragment_race_list) {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: RaceListViewModel by viewModels { viewModelFactory }
    private val seasonProvider: SelectedSeasonProvider by activityViewModels<SeasonPickerViewModel> { viewModelFactory }

    private var _binding: FragmentRaceListBinding? = null
    private val binding get() = _binding!!

    private val adapter: RaceListRecyclerViewAdapter by lazy {
        val headerItemListener = getOnHeaderItemSelectedListener()
        val eventItemListener = getOnEventItemSelectedListener()
        RaceListRecyclerViewAdapter(headerItemListener, eventItemListener)
    }

    override fun onAttach(context: Context) {
        (activity?.application as F1Application).f1Component.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRaceListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAppBar()
        setupRecyclerView()
        setupViewModelObserver()
        setupPickerViewModelObserver()

    }

    private fun setupAppBar() {
        binding.raceListTopAppBar.run {
            title = seasonProvider.season.value
            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.action_pick_season -> {
                        val action = RaceListFragmentDirections.actionRaceListFragmentToSeasonPickFragment()
                        findNavController().navigate(action)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun setupViewModelObserver() {
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
    }

    private fun setupPickerViewModelObserver() {
        seasonProvider.season.observe(viewLifecycleOwner) { season ->
            viewModel.fetchUiState(season = season)
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

    private fun getOnHeaderItemSelectedListener(): OnHeaderItemSelectedListener {
        return object : OnHeaderItemSelectedListener {
            override fun toggleHeader(header: RaceWeekListItem.Header) {
                viewModel.toggleCollapsedHeader(header = header)
            }

            override fun showDetails(header: RaceWeekListItem.Header) {
                //todo: add animation
                val circuitId = header.circuitId
                val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(
                    circuitId,
                    seasonProvider.season.value
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun getOnEventItemSelectedListener(): OnEventItemSelectedListener {
        return object : OnEventItemSelectedListener {
            override fun showDetails(event: RaceWeekListItem.Event) {
                //todo: add animation
                val circuitId = event.circuitId
                val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(
                    circuitId,
                    seasonProvider.season.value
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}