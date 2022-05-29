package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainScreenBinding
import com.example.weatherapp.ui.viewmodels.MainSearchScreenViewModel
import com.example.weatherapp.util.snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen)
{
	
	private var _binding: FragmentMainScreenBinding? = null
	private val binding: FragmentMainScreenBinding
		get() = _binding!!
	
	private val viewModel: MainSearchScreenViewModel by viewModels()
	
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
		
	}
	
	private fun listenToEvents() = lifecycleScope.launchWhenStarted {
		
		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is MainSearchScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					snackbar(event.error)
				}
				else                                                                    ->
				{
					Unit
				}
			}
		}
	}
	
	private fun subscribeToObservers() = lifecycleScope.launchWhenStarted {
		viewModel.screen.collect { event ->
			when (event)
			{
				is MainSearchScreenViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						binding.apply {
							cityTextView.text = event.city
							humidityTextView.text = getString(R.string.humidity_value, daily[0].humidity, "%")
							cloudsTextView.text = getString(R.string.clouds_value, daily[0].clouds, "%")
							tempTextView.text = getString(R.string.temp_value, current.temp, "°C")
							minTextView.text = getString(R.string.min_value, daily[0].temp.min, "°C")
							maxTextView.text = getString(R.string.max_value, daily[0].temp.max, "°C")
						}
					}
					
					binding.loadingSpinner.isVisible = false
					binding.card.isVisible = true
					binding.btnForecast.isVisible = true
					binding.btnChangeCity.isVisible = true
				}
				is MainSearchScreenViewModel.SetupEvent.MainScreenLoadingEvent     ->
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