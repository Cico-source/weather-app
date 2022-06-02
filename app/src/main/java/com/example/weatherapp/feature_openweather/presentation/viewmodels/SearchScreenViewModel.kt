package com.example.weatherapp.feature_openweather.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails
import com.example.weatherapp.feature_openweather.domain.repository.OpenWeatherRepository
import com.example.weatherapp.common.util.Constants
import com.example.weatherapp.common.util.DispatcherProvider
import com.example.weatherapp.common.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
	private val repository: OpenWeatherRepository,
	private val dispatchers: DispatcherProvider
) : ViewModel()
{
	
	sealed class SetupEvent
	{
		
		data class GetCityWeatherDetailsEvent(val weatherDetails: WeatherDetails, val city: String) : SetupEvent()
		data class GetCityWeatherDetailsErrorEvent(val error: String) : SetupEvent()
		
		object MainScreenLoadingEvent : SetupEvent()
		object MainScreenEmptyEvent : SetupEvent()
	}
	
	private val _setupEvent = MutableSharedFlow<SetupEvent>()
	val setupEvent: SharedFlow<SetupEvent> = _setupEvent
	
	private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.MainScreenEmptyEvent)
	val screen: StateFlow<SetupEvent> = _screen
	
	fun getWeatherDetailsForCity(city: String)
	{
		_screen.value = SetupEvent.MainScreenLoadingEvent
		
		viewModelScope.launch(dispatchers.main) {
			
			val cityCoords = repository.getCoordinatesForCity(city, 0)
			
			val (lat, lon) = if (cityCoords is Resource.Success)
			{
				cityCoords.data!!.find {
					it.country == Constants.cities[city]
				}!!
			}
			else
			{
				_screen.emit(SetupEvent.MainScreenEmptyEvent)
				_setupEvent.emit(SetupEvent.GetCityWeatherDetailsErrorEvent(cityCoords.message ?: return@launch))
				return@launch
			}
			
			val cityWeatherDetails = repository.getWeatherDetailsByCityCoords(lat.toString(), lon.toString(), caching = false)
			
			if (cityWeatherDetails is Resource.Success)
			{
				_screen.value = SetupEvent.GetCityWeatherDetailsEvent(cityWeatherDetails.data ?: return@launch, city)
			}
			else
			{
				_screen.emit(SetupEvent.MainScreenEmptyEvent)
				_setupEvent.emit(SetupEvent.GetCityWeatherDetailsErrorEvent(cityWeatherDetails.message ?: return@launch))
			}
			
		}
	}
}