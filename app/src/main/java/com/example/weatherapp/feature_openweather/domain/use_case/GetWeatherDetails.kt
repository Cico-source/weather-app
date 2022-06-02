package com.example.weatherapp.feature_openweather.domain.use_case

import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails
import com.example.weatherapp.feature_openweather.domain.repository.OpenWeatherRepository
import com.example.weatherapp.common.util.Resource

class GetWeatherDetails(private val repository: OpenWeatherRepository)
{
	suspend operator fun invoke(lat:String, lon: String, caching: Boolean = true, units: String = "metric"): Resource<WeatherDetails>
	{
		return repository.getWeatherDetailsByCityCoords(lat, lon, caching, units)
	}
}