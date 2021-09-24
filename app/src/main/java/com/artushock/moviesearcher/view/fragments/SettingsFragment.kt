package com.artushock.moviesearcher.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.artushock.moviesearcher.R
import com.artushock.moviesearcher.databinding.FragmentSettingsBinding

const val APP_PREFERENCES_KEY = "APP_PREFERENCES_KEY"
const val ADULT_CONTENT_SHOW_KEY = "ADULT_CONTENT_SHOW_KEY"
const val CARTOONS_SHOW_KEY = "CARTOONS_SHOW_KEY"

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettingsElements()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun initSettingsElements() {
        val adultSwitch = binding.adultContentSwitch
        val cartoonSwitch = binding.cartoonShovingSwitch

        initAdultSummary(adultSwitch)
        initCartoonSummary(cartoonSwitch)

        adultSwitch.setOnCheckedChangeListener { button, isChecked ->
            run {
                activity?.let {
                    with(
                        it.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE).edit()
                    ) {
                        putBoolean(ADULT_CONTENT_SHOW_KEY, isChecked)
                        apply()
                    }
                }
                initAdultSummary(button as Switch)
            }
        }

        cartoonSwitch.setOnCheckedChangeListener { button, isChecked ->
            run {
                activity?.let {
                    with(
                        it.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE).edit()
                    ) {
                        putBoolean(CARTOONS_SHOW_KEY, isChecked)
                        apply()
                    }
                }
                initCartoonSummary(button as Switch)
            }
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun initAdultSummary(adultSwitch: Switch) {
        val summaryAdult = binding.adultsSwitchSummary
        activity?.let {
            if (it.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE)
                    .getBoolean(ADULT_CONTENT_SHOW_KEY, false)
            ) {
                summaryAdult.text = getText(R.string.turn_off_adult_content)
                adultSwitch.isChecked = true
            } else {
                summaryAdult.text = getText(R.string.turn_on_adult_content)
                adultSwitch.isChecked = false
            }
        }
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun initCartoonSummary(cartoonSwitch: Switch) {
        val summaryCartoon = binding.cartoonsSwitchSummary
        activity?.let {
            if (it.getSharedPreferences(APP_PREFERENCES_KEY, Context.MODE_PRIVATE).getBoolean(CARTOONS_SHOW_KEY, false)) {
                summaryCartoon.text = getText(R.string.turn_off_cartoons)
                cartoonSwitch.isChecked = true
            } else {
                summaryCartoon.text = getText(R.string.turn_on_cartoons)
                cartoonSwitch.isChecked = false
            }
        }
    }
}