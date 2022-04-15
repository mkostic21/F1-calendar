package com.example.f1_calendar.ui.fragments.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.f1_calendar.F1Application
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentDetailsBinding
import com.example.f1_calendar.model.ui.details.DetailsFragmentUiState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details), OnMapReadyCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DetailsViewModel by viewModels { viewModelFactory }

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity?.application as F1Application).f1Component.inject(this)
        binding = FragmentDetailsBinding.bind(view)

        viewModel.fetchUiState(circuitId = args.circuitId, season = args.season)
        binding.map.onCreate(savedInstanceState)

        viewModel.uiState.observe(viewLifecycleOwner){ state ->
            when (state) {
                is DetailsFragmentUiState.Success -> {
                    binding.map.getMapAsync(this)
                }
                is DetailsFragmentUiState.Error -> {
                    Log.d("Response", state.t.toString())
                }
                is DetailsFragmentUiState.Loading -> {
                    Log.d("Response", "Loading...")
                }
            }
        }

        binding.btnMoreInfo.setOnClickListener {
            val builder = CustomTabsIntent.Builder()
            context?.let { context ->
                builder.build().launchUrl(
                    context,
                    Uri.parse(viewModel.getUrl())
                )
            }
        }
    }

    override fun onMapReady(mMap: GoogleMap) {
        val loc = viewModel.getLocation()
        val location = LatLng(loc.lat.toDouble(), loc.long.toDouble())
        mMap.addMarker(MarkerOptions().position(location))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 14f))
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
