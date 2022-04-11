package com.example.f1_calendar.ui.fragments.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.f1_calendar.R
import com.example.f1_calendar.databinding.FragmentDetailsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class DetailsFragment : Fragment(R.layout.fragment_details), OnMapReadyCallback {

    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
    }

    override fun onMapReady(mMap: GoogleMap) {
        val bahrain = LatLng(26.0325, 50.5106)
        mMap.addMarker(MarkerOptions().position(bahrain).title("Marker in Bahrain"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(bahrain, 14f))
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
