package com.example.weatherapp.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.remote.responses.WeatherDetailsResponse
import com.example.weatherapp.repository.OpenWeatherRepository
import com.example.weatherapp.util.DispatcherProvider
import com.example.weatherapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
	private val repository: OpenWeatherRepository,
	private val dispatchers: DispatcherProvider
) : ViewModel()
{
	
	sealed class SetupEvent
	{
		data class GetCityWeatherDetailsEvent(val weatherDetails: WeatherDetailsResponse, val city:String) : SetupEvent()
		data class GetCityWeatherDetailsErrorEvent(val error: String) : SetupEvent()
		
		object MainScreenLoadingEvent : SetupEvent()
		object MainScreenEmptyEvent : SetupEvent()

//		data class JoinRoomEvent(val roomName: String) : SetupEvent()
//		data class JoinRoomErrorEvent(val error: String) : SetupEvent()
	}
	
	private val _setupEvent = MutableSharedFlow<SetupEvent>()
	val setupEvent: SharedFlow<SetupEvent> = _setupEvent
	
	private val _screen = MutableStateFlow<SetupEvent>(SetupEvent.MainScreenEmptyEvent)
	val screen: StateFlow<SetupEvent> = _screen
	
	fun getWeatherDetailsForCity(city: String)
	{
		_screen.value = SetupEvent.MainScreenLoadingEvent
		viewModelScope.launch(dispatchers.main) {
			
			repository.getCoordinatesForCity(city).data!!.run {
				
				val cityWeatherDetails = repository.getWeatherDetailsByCityCoords(this[0].lat.toString(), this[0].lon.toString())
				
				if (cityWeatherDetails is Resource.Success)
				{
					_screen.value = SetupEvent.GetCityWeatherDetailsEvent(cityWeatherDetails.data ?: return@launch, city)
				}
				else
				{
					_setupEvent.emit(SetupEvent.GetCityWeatherDetailsErrorEvent(cityWeatherDetails.message ?: return@launch))
				}
			}
		}
	}
	
}