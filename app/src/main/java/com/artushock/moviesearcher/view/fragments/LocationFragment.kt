package com.artushock.moviesearcher.view.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.databinding.FragmentLocationBinding

private const val REFRESH_PERIOD = 60000L
private const val MINIMAL_DISTANCE = 100f

class LocationFragment : Fragment() {
    private var _binding: FragmentLocationBinding? = null
    private val binding get() = _binding!!

    private val permissionFineLocationAccess =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                this.getLocation()
            } else {
                showExplanationAfterDenyPermission()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    this.getLocation()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showExplanationBeforeRequestPermission()
                }
                else -> {
                    requestPermission()
                }
            }
        }
    }

    private fun requestPermission() {
        permissionFineLocationAccess.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        Toast.makeText(context, "getLocation()", Toast.LENGTH_SHORT).show()
        val locationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val provider = locationManager.getProvider(LocationManager.GPS_PROVIDER)

            provider?.let {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    REFRESH_PERIOD,
                    MINIMAL_DISTANCE,
                    object : LocationListener {
                        override fun onLocationChanged(location: Location) {
                            getAddressByLocation(location)
                        }

                        override fun onStatusChanged(
                            provider: String?,
                            status: Int,
                            extras: Bundle?
                        ) {
                            //
                        }

                        override fun onProviderEnabled(provider: String) {
                            //
                        }

                        override fun onProviderDisabled(provider: String) {
                            //
                        }

                    }
                )
            }
        } else {
            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)?.let { location ->
                getAddressByLocation(location)
            } ?: run {
                Toast.makeText(context, "Неизвестное местоположение", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getAddressByLocation(location: Location) {
        val geocoder = Geocoder(context)

        Thread {
            try {
                val address = geocoder.getFromLocation(
                    location.latitude,
                    location.longitude,
                    1
                )

                binding.textMap.post {
                    binding.textMap.text = address[0].getAddressLine(0)
                    AlertDialog.Builder(context)
                        .setMessage(address[0].getAddressLine(0))
                        .create()
                        .show()
                }

            } catch (e: Exception) {
                e.stackTrace
            }
        }.start()
    }

    private fun showExplanationBeforeRequestPermission() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Доступ к местоположению")
                .setMessage("Для показа карты необходим доступ к Вашему местоположению")
                .setPositiveButton("Ok") { _, _ ->
                    requestPermission()
                }
                .setNegativeButton("Отмена") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }

    private fun showExplanationAfterDenyPermission() {
        context?.let {
            AlertDialog.Builder(it)
                .setTitle("Доступ к местоположению")
                .setMessage("Без доступа к местоположению отображение карты невозможно. Извините!")
                .setNegativeButton("Закрыть") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
    }
}
