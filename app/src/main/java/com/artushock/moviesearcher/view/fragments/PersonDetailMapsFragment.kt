package com.artushock.moviesearcher.view.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentActorDetailMapsBinding
import com.artushock.moviesearcher.model.dto.PersonDTO
import com.artushock.moviesearcher.model.states.PersonDetailState
import com.artushock.moviesearcher.viewmodel.PersonDetailMapsViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_actor_detail_maps.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PersonDetailMapsFragment : Fragment() {

    private var _binding: FragmentActorDetailMapsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PersonDetailMapsViewModel by lazy {
        ViewModelProvider(this).get(PersonDetailMapsViewModel::class.java)
    }

    private var personLocation: LatLng = LatLng(-34.0, 151.0)
    private var personAddress: String = ""

    private val callback = OnMapReadyCallback { googleMap ->
        googleMap.addMarker(MarkerOptions().position(personLocation).title(personAddress))
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(personLocation, 12f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorDetailMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actorId = arguments?.getInt(ACTOR_ID)
        viewModel.personDetailLiveData.observe(viewLifecycleOwner, { render(it) })
        actorId?.let { viewModel.getPersonDetailFromServer(it) }
    }

    private fun render(state: PersonDetailState?) {
        when (state) {
            is PersonDetailState.Error -> {
                binding.loadingLayout.fragmentProgressBar.visibility = View.GONE
                binding.actorNameDetailTv.text = getString(R.string.download_error)
                binding.actorFullDetailTv.text = state.e.toString()
            }
            is PersonDetailState.Loading -> {
                binding.loadingLayout.fragmentProgressBar.visibility = View.VISIBLE
            }
            is PersonDetailState.Success -> {
                binding.loadingLayout.fragmentProgressBar.visibility = View.GONE
                setPersonData(state.personDetail)
                showPlaceOfBirthOnMap(state.personDetail.place_of_birth)
            }
        }
    }

    private fun setPersonData(personDetail: PersonDTO) {
        binding.actorNameDetailTv.text = personDetail.name
        binding.actorFullDetailTv.text = personDetail.place_of_birth

        Picasso
            .get()
            .load("https://image.tmdb.org/t/p/w500/${personDetail.profile_path}")
            .into(profile_iv)
    }

    private fun showPlaceOfBirthOnMap(placeOfBirth: String) {
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        personAddress = placeOfBirth
        lifecycleScope.launch {
            personLocation = async { getLatLngByAddress(placeOfBirth) }.await()
            mapFragment?.getMapAsync(callback)
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