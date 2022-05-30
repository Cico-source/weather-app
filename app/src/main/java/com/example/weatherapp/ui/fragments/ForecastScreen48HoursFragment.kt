package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastScreen48HoursBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastScreen48HoursFragment : Fragment(R.layout.fragment_forecast_screen48_hours)
{
	
	private var _binding: FragmentForecastScreen48HoursBinding? = null
	private val binding: FragmentForecastScreen48HoursBinding
		get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen48HoursBinding.bind(view)
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}
