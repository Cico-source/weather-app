package com.example.weatherapp.feature_openweather.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainScreenBinding
import com.example.weatherapp.feature_openweather.presentation.viewmodels.MainScreenViewModel
import com.example.weatherapp.common.util.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen)
{
	
	private var _binding: FragmentMainScreenBinding? = null
	private val binding: FragmentMainScreenBinding
		get() = _binding!!
	
	private val viewModel: MainScreenViewModel by viewModels()
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentMainScreenBinding.bind(view)
		
		subscribeToObservers()
		listenToEvents()
		
		viewModel.getWeatherDetailsForCity("City of Zagreb")
	
		
		binding.btnChangeCity.setOnClickListener {
			
			findNavController().navigate(R.id.action_mainScreenFragment_to_searchScreenFragment)
		}
		
		binding.btnForecast.setOnClickListener {
			
			findNavController().navigate(R.id.action_mainScreenFragment_to_forecastScreenFragment)
		}
		
		binding.btnRefresh.setOnClickListener {
			
			binding.loadingSpinner.isVisible = true
			binding.btnRefresh.isVisible = false
			viewModel.getWeatherDetailsForCity("City of Zagreb")
		}
		
	}
	
	private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
		
		viewModel.setupEvent.collect { event ->
			
			when (event)
			{
				
				is MainScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
				else                                                              ->
				{
					Unit
				}
			}
		}
		
	}
	
	private fun subscribeToObservers() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
		
		viewModel.screen.collect { event ->
			when (event)
			{
				is MainScreenViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						binding.apply {
							cityTextView.text = event.city
							humidityTextView.text = getString(R.string.humidity_value, dailyDto[0].humidity, "%")
							cloudsTextView.text = getString(R.string.clouds_value, dailyDto[0].clouds, "%")
							tempTextView.text = getString(R.string.temp_value, currentDto.temp, "°C")
							minTextView.text = getString(R.string.min_value, dailyDto[0].tempDto.min, "°C")
							maxTextView.text = getString(R.string.max_value, dailyDto[0].tempDto.max, "°C")
						}
					}
					
					binding.loadingSpinner.isVisible = false
					binding.cards.isVisible = true
					binding.btnForecast.isVisible = true
					binding.btnChangeCity.isVisible = true
				}
				is MainScreenViewModel.SetupEvent.MainScreenLoadingEvent     ->
				{
					binding.loadingSpinner.isVisible = true
				}
				
				else                                                     ->
				{
					Unit
				}
			}
		}
		
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}