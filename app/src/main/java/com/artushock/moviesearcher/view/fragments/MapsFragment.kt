package com.artushock.moviesearcher.view.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.artushock.moviesearcher.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(51.282742, 30.208725)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }

    private val searchCallback = OnMapReadyCallback { googleMap ->
        val address = getAddressStr()

        lifecycleScope.launch {
            val latLng = async { getLatLngByAddress(address) }
            googleMap.addMarker(MarkerOptions().position(latLng.await()).title("Marker in Sydney"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng.await(), 12f))
        }
    }

    private fun getAddressStr(): String {
        return view?.findViewById<EditText>(R.id.address_edt_text)?.text.toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        view.findViewById<Button>(R.id.search_on_map_btn)
            .setOnClickListener {
                mapFragment?.getMapAsync(searchCallback)
            }
    }

    private fun getLatLngByAddress(address: String): LatLng {
        val geocoder = Geocoder(context)
        val location = geocoder.getFromLocationName(address, 1)
        with(location[0]) {
            return LatLng(latitude, longitude)
        }
    }
}



