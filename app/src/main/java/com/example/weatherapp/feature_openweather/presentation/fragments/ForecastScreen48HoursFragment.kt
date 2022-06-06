package com.example.weatherapp.feature_openweather.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.common.util.SmoothScrolling
import com.example.weatherapp.common.util.snackbar
import com.example.weatherapp.databinding.FragmentForecastScreen48HoursBinding
import com.example.weatherapp.feature_openweather.domain.model.Hourly
import com.example.weatherapp.feature_openweather.presentation.adapters.Hours48RecyclerViewAdapter
import com.example.weatherapp.feature_openweather.presentation.viewmodels.ForecastScreen48HoursViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ForecastScreen48HoursFragment : Fragment(R.layout.fragment_forecast_screen48_hours)
{
	
	private var _binding: FragmentForecastScreen48HoursBinding? = null
	private val binding: FragmentForecastScreen48HoursBinding
		get() = _binding!!
	
	@Inject
	lateinit var hours48Adapter: Hours48RecyclerViewAdapter
	
	private var updateHours48Job: Job? = null
	
	private val viewModel: ForecastScreen48HoursViewModel by viewModels()
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen48HoursBinding.bind(view)
		
		binding.rvList.layoutManager = SmoothScrolling(requireActivity(), LinearLayoutManager.VERTICAL)
		binding.rvList.adapter = hours48Adapter
		
		listenToEvents()
		subscribeToObservers()
		
		viewModel.getWeatherDetailsForCity("City of Zagreb")
		
		hours48Adapter.setOnItemClickListener { position: Int ->
			
			binding.rvList.smoothScrollToPosition(position)
		}
		
		binding.btnRefresh.setOnClickListener {
			
			binding.loadingSpinner.isVisible = true
			binding.btnRefresh.isVisible = false
			viewModel.getWeatherDetailsForCity("City of Zagreb")
		}
		
	}
	
	private fun updateHours48RecyclerView(days7: List<Hourly>)
	{
		updateHours48Job?.cancel()
		updateHours48Job = lifecycleScope.launch {
			
			hours48Adapter.updateDataset(days7)
		}
		
	}
	
	private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
		
		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is ForecastScreen48HoursViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					binding.btnRefresh.isVisible = true
					snackbar(event.error)
				}
				else                                                                         ->
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
				is ForecastScreen48HoursViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						
						updateHours48RecyclerView(hourlyDto)
					}
					
					binding.loadingSpinner.isVisible = false
				}
				is ForecastScreen48HoursViewModel.SetupEvent.MainScreenLoadingEvent     ->
				{
					binding.loadingSpinner.isVisible = true
				}
				
				else                                                                    ->
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
