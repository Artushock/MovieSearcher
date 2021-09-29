package com.artushock.moviesearcher.view.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.databinding.FragmentMapBinding

class MapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val permissionFineLocationAccess =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                showMap()
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
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED -> {
                    showMap()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                    showExplanationBeforeRequestPermission(it)
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

    private fun showMap() {
        //todo
        Toast.makeText(context, "showMap()", Toast.LENGTH_SHORT).show()
    }

    private fun showExplanationBeforeRequestPermission(it: Context) {
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
