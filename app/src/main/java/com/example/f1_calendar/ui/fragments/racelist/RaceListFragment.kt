package com.example.f1_calendar.ui.fragments.racelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1_calendar.R
import com.example.f1_calendar.adapter.RaceListRecyclerViewAdapter
import com.example.f1_calendar.databinding.FragmentRaceListBinding
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiState
import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem
import com.example.f1_calendar.ui.fragments.seasonpick.SeasonPickerViewModel
import com.example.f1_calendar.ui.fragments.seasonpick.SelectedSeasonProvider
import com.example.f1_calendar.util.toPx
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RaceListFragment : Fragment(R.layout.fragment_race_list) {
    private val viewModel: RaceListViewModel by viewModels()
    private val seasonProvider: SelectedSeasonProvider by activityViewModels<SeasonPickerViewModel>()

    private var _binding: FragmentRaceListBinding? = null
    private val binding get() = _binding!!

    private val adapter: RaceListRecyclerViewAdapter by lazy {
        val headerItemListener = getOnHeaderItemSelectedListener()
        val eventItemListener = getOnEventItemSelectedListener()
        RaceListRecyclerViewAdapter(headerItemListener, eventItemListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashScreen = activity?.installSplashScreen()
        splashScreen?.setKeepOnScreenCondition {
            viewModel.uiState.value is RaceListFragmentUiState.Loading
        }
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
        setupNextRaceIdObserver()
        setupRetryButton()
    }

    private fun setupRetryButton() {
        binding.btnRetry.setOnClickListener {
            Log.d("response", "retryButton pressed!")
            viewModel.fetchUiState(season = seasonProvider.season.value!!)
        }
    }

    private fun setupAppBar() {
        binding.raceListTopAppBar.run {
            title = seasonProvider.season.value
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_pick_season -> {
                        val action =
                            RaceListFragmentDirections.actionRaceListFragmentToSeasonPickFragment()
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
                    Log.d("response", state.toString())
                    adapter.submitList(state.listItems)
                    hideProgressBar()
                    hideNetworkError()
                    binding.rvRaceList.visibility = View.VISIBLE
                }
                is RaceListFragmentUiState.Error -> {
                    Log.d("Response", state.t.toString())
                    showNetworkError()
                    hideProgressBar()
                }
                is RaceListFragmentUiState.Loading -> {
                    Log.d("Response", "Loading...")
                    hideNetworkError()
                    showProgressBar()
                }
            }
        }
    }

    private fun hideNetworkError() {
        Log.d("Response", "Hiding network error")
        binding.rvRaceList.visibility = View.VISIBLE
        binding.groupEmptyViews.visibility = View.GONE
    }

    private fun showNetworkError() {
        Log.d("Response", "Showing network error")
        binding.rvRaceList.visibility = View.GONE
        binding.groupEmptyViews.visibility = View.VISIBLE
    }

    private fun setupNextRaceIdObserver(){
        viewModel.nextRaceId.observe(viewLifecycleOwner){ id ->
            if(viewModel.shouldScroll()){
                binding.rvRaceList.smoothScrollToPosition(id)
                Log.d("response", "scrolled to id: $id")
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
        binding.rvRaceList.visibility = View.GONE
    }

    private fun hideProgressBar() {
        Log.d("Response", "Hiding progressbar")
        binding.progressCircular.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        binding.rvRaceList.adapter = adapter
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.rvRaceList.layoutManager = layoutManager
        addItemDecoration()
    }

    private fun addItemDecoration() {
        val decorationHeight = 1.toPx
        context?.let {
            binding.rvRaceList.addItemDecoration(
                DividerItemDecoration(
                    ContextCompat.getColor(it, R.color.black), heightInPixels = decorationHeight
                )
            )
        }
    }

    private fun getOnHeaderItemSelectedListener(): OnHeaderItemSelectedListener {
        return object : OnHeaderItemSelectedListener {
            override fun toggleHeader(header: RaceWeekListItem.Header) {
                viewModel.toggleCollapsedHeader(header = header)
            }

            override fun showDetails(header: RaceWeekListItem.Header) {
                val circuitId = header.circuitId
                val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(
                    circuitId,
                    seasonProvider.season.value!!
                )
                findNavController().navigate(action)
            }
        }
    }

    private fun getOnEventItemSelectedListener(): OnEventItemSelectedListener {
        return object : OnEventItemSelectedListener {
            override fun showDetails(event: RaceWeekListItem.Event) {
                val circuitId = event.circuitId
                val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(
                    circuitId,
                    seasonProvider.season.value!!
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