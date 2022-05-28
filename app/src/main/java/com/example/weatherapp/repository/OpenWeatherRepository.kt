package com.example.weatherapp.repository

import com.example.weatherapp.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.data.remote.responses.WeatherDetailsResponse
import com.example.weatherapp.util.Resource

interface OpenWeatherRepository
{
	suspend fun getCoordinatesForCity(city: String): Resource<List<CityCoordinatesResponse>>
	
	suspend fun getWeatherDetailsByCityCoords(lat: String, lon: String, units: String = "metric"):
			Resource<WeatherDetailsResponse>
	
}