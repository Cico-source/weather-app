package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.OpenWeatherRepository
import com.example.weatherapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ForecastScreenViewModel @Inject constructor(
	private val repository: OpenWeatherRepository,
	private val dispatchers: DispatcherProvider
) : ViewModel()
{

}