package com.artushock.moviesearcher.view.fragments

import android.location.Geocoder
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    val TAG = "MapsFragment"
    private lateinit var listener: LocationListener

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(51.282742, 30.208725)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12f))
    }

    private val searchCallback = OnMapReadyCallback { googleMap ->
        val address = getAddressStr()
        Log.d(TAG, "searchCallback - address: $address")
        Thread { getLatLngByAddress(address) }.start()
        listener = object : LocationListener {
            override fun onLocationGotten(location: LatLng) {
                googleMap.addMarker(MarkerOptions().position(location).title("Marker in Sydney"))
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12f))
            }
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

    private fun getLatLngByAddress(address: String) {
        val handler = Handler(Looper.getMainLooper())

        try {
            val geocoder = Geocoder(context)
            val location = geocoder.getFromLocationName(address, 1)

            Log.d(TAG, "getLatLngByAddress - location.size: ${location.size}")
            handler.post {
                if (location.isNotEmpty()) {
                    Log.d(TAG, "getLatLngByAddress in handler - location.size: ${location.size}")
                    with(location[0]) {
                        listener.onLocationGotten(LatLng(latitude, longitude))
                    }
                }
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    interface LocationListener {
        fun onLocationGotten(location: LatLng)
    }
}



