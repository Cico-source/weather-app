package com.example.weatherapp.feature_openweather.domain.use_case

import com.example.weatherapp.feature_openweather.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.feature_openweather.domain.repository.OpenWeatherRepository
import com.example.weatherapp.common.util.Resource

class GetCityCoordinates(private val repository: OpenWeatherRepository)
{
	suspend operator fun invoke(city: String, cacheDuration: Int): Resource<List<CityCoordinatesResponse>>
	{
		return repository.getCoordinatesForCity(city, cacheDuration)
	}
}