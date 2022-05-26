package com.example.weatherapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.api.OpenWeatherApi
import com.example.weatherapp.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val openWeatherApi: OpenWeatherApi,
	private val dispatchers: DispatcherProvider
): ViewModel()
{
	fun dummyApiCall()
	{
		viewModelScope.launch(dispatchers.io) {
			
			val response = openWeatherApi.getCoordinatesByZipAndCountryCode("E14,GB")
			val (lat, lon) = response.body()!!
			
			Log.i("FFF", "$lat, $lon")
		}
	}
}