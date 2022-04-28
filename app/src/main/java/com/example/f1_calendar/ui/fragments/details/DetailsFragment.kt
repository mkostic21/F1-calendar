package com.example.f1_calendar.ui.fragments.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentDetailsBinding
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiState
import com.example.f1_calendar.util.Constants.Companion.MAP_ZOOM
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val viewModel: DetailsViewModel by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUiState(circuitId = args.circuitId, season = args.season)
        binding.map.onCreate(savedInstanceState)
        setupViewModelObserver()
    }

    private fun setupAppBar(url: String) {
        binding.detailsTopAppBar.run {
            setNavigationOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_read_more -> {
                        val builder = CustomTabsIntent.Builder()
                        context?.let { context ->
                            builder.build().launchUrl(
                                context,
                                Uri.parse(url)
                            )
                        }
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
                is DetailsFragmentUiState.Success -> {
                    setupAppBar(state.url)
                    binding.map.getMapAsync { mMap ->
                        mMap.setOnMapLoadedCallback {
                            val location = LatLng(state.lat.toDouble(), state.long.toDouble())
                            mMap.addMarker(MarkerOptions().position(location))
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, MAP_ZOOM))
                        }
                    }
                }
                is DetailsFragmentUiState.Error -> {
                    Log.d("Response", state.t.toString())
                }
                is DetailsFragmentUiState.Loading -> {
                    Log.d("Response", "Loading...")
                }
                is DetailsFragmentUiState.Empty -> {
                    binding.run {
                        map.visibility = View.GONE
                        tvEmpty.visibility = View.VISIBLE
                        tvEmpty.text = resources.getString(R.string.details_error)
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.map.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.map.onSaveInstanceState(outState)
    }
}
