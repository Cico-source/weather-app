package com.example.weatherapp.repository

import com.example.weatherapp.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.util.Resource

interface OpenWeatherRepository
{
	suspend fun getCoordinatesForCity(city: String): Resource<List<CityCoordinatesResponse>>
}