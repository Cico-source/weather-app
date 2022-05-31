package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.Days7RecyclerViewAdapter
import com.example.weatherapp.data.remote.models.Daily
import com.example.weatherapp.databinding.FragmentForecastScreen7DaysBinding
import com.example.weatherapp.ui.viewmodels.ForecastScreenViewModel
import com.example.weatherapp.util.snackbar
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
	
	private val viewModel: ForecastScreenViewModel by viewModels()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen7DaysBinding.bind(view)
		
		binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
		binding.rvList.adapter = days7Adapter
		
		listenToEvents()
		subscribeToObservers()
		
		viewModel.getWeatherDetailsForCity("City of Zagreb")
	}
	
	private fun updateDays7RecyclerView(days7: List<Daily>)
	{
		updateDays7Job?.cancel()
		updateDays7Job = lifecycleScope.launch {
			
			days7Adapter.updateDataset(days7)
		}
	}
	
	private fun listenToEvents() = lifecycleScope.launchWhenStarted {

		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is ForecastScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
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
				is ForecastScreenViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						
						updateDays7RecyclerView(daily)
					}

					binding.loadingSpinner.isVisible = false
				}
				is ForecastScreenViewModel.SetupEvent.MainScreenLoadingEvent     ->
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