package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentForecastScreen7DaysBinding

class ForecastScreen7DaysFragment : Fragment(R.layout.fragment_forecast_screen7_days)
{
	
	private var _binding: FragmentForecastScreen7DaysBinding? = null
	private val binding: FragmentForecastScreen7DaysBinding
		get() = _binding!!
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen7DaysBinding.bind(view)
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}