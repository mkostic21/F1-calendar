package com.example.f1_calendar.ui.fragments.racelist

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.adapter.RaceListRecyclerViewAdapter
import com.example.f1_calendar.api.F1Api
import com.example.f1_calendar.databinding.FragmentRaceListBinding
import com.example.f1_calendar.domain.F1ApiRaceTableRepository
import com.example.f1_calendar.model.ui.racelist.RaceListFragmentUiState
import com.example.f1_calendar.model.ui.racelist.RaceWeekListItem
import javax.inject.Inject

class RaceListFragment : Fragment(R.layout.fragment_race_list), OnHeaderItemSelectedListener,
    OnEventItemSelectedListener {

    @Inject
    lateinit var api: F1Api

    private lateinit var binding: FragmentRaceListBinding
    private lateinit var adapter: RaceListRecyclerViewAdapter
    private lateinit var viewModel: RaceListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as F1Application).f1Component.inject(this)

        binding = FragmentRaceListBinding.bind(view)

        val repository = F1ApiRaceTableRepository(api)
        val viewModelProviderFactory = RaceListViewModelProviderFactory(repository)
        viewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(RaceListViewModel::class.java)


        setupRecyclerView()


        viewModel.uiState.observe(viewLifecycleOwner) {
            when (it) {
                is RaceListFragmentUiState.Success -> {
                    adapter.submitList(it.listItems)
                    hideProgressBar()
                }
                is RaceListFragmentUiState.Error -> {
                    Log.d("Response", it.t.toString())
                    hideProgressBar()
                }
                is RaceListFragmentUiState.Loading -> {
                    Log.d("Response", "Loading...")
                    showProgressBar()
                }
            }
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
        adapter = RaceListRecyclerViewAdapter(this, this)
        binding.rvRaceList.adapter = adapter
        binding.rvRaceList.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

    }


    override fun onHeaderItemSelected(header: RaceWeekListItem.Header) {
        viewModel.toggleCollapsedHeader(header = header)
    }

    override fun onEventItemSelected(event: RaceWeekListItem) {
        //todo: animation
        val circuitId = (event as RaceWeekListItem.Event).circuitId
        val action = RaceListFragmentDirections.actionRaceListFragmentToDetailsFragment(circuitId)
        findNavController().navigate(action)
    }
}