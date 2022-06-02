package com.example.weatherapp.feature_openweather.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.feature_openweather.presentation.adapters.Days7RecyclerViewAdapter
import com.example.weatherapp.databinding.FragmentForecastScreen7DaysBinding
import com.example.weatherapp.feature_openweather.domain.model.Daily
import com.example.weatherapp.feature_openweather.presentation.viewmodels.ForecastScreen7DaysViewModel
import com.example.weatherapp.common.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ForecastScreen7DaysFragment : Fragment(R.layout.fragment_forecast_screen7_days)
{
	
	private var _binding: FragmentForecastScreen7DaysBinding? = null
	private val binding: FragmentForecastScreen7DaysBinding
		get() = _binding!!
	
	@Inject
	lateinit var days7Adapter: Days7RecyclerViewAdapter
	
	private var updateDays7Job: Job? = null
	
	private val viewModel: ForecastScreen7DaysViewModel by viewModels()
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen7DaysBinding.bind(view)
		
		binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
		binding.rvList.adapter = days7Adapter
		
		listenToEvents()
		subscribeToObservers()
		
		viewModel.getWeatherDetailsForCity("City of Zagreb")
		
		binding.btnRefresh.setOnClickListener {
			
			binding.loadingSpinner.isVisible = true
			binding.btnRefresh.isVisible = false
			viewModel.getWeatherDetailsForCity("City of Zagreb")
		}
		
	}
	
	private fun updateDays7RecyclerView(days7: List<Daily>)
	{
		updateDays7Job?.cancel()
		updateDays7Job = lifecycleScope.launch {
			
			days7Adapter.updateDataset(days7)
		}
	}
	
	private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {

		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is ForecastScreen7DaysViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
				else                                                                       ->
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
				is ForecastScreen7DaysViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						
						updateDays7RecyclerView(dailyDto)
					}

					binding.loadingSpinner.isVisible = false
				}
				is ForecastScreen7DaysViewModel.SetupEvent.MainScreenLoadingEvent     ->
				{
					binding.loadingSpinner.isVisible = true
				}

				else                                                               ->
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