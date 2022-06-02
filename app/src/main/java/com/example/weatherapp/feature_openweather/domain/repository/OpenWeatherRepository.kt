package com.example.weatherapp.feature_openweather.domain.repository

import com.example.weatherapp.feature_openweather.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails
import com.example.weatherapp.common.util.Resource

interface OpenWeatherRepository
{
	suspend fun getCoordinatesForCity(city: String, cacheDuration: Int, limit: Int = 5): Resource<List<CityCoordinatesResponse>>
	
	suspend fun getWeatherDetailsByCityCoords(lat: String, lon: String, caching: Boolean = true, units: String = "metric"):
			Resource<WeatherDetails>
	
}