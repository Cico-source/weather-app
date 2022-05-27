package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.remote.responses.CityCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi
{
	@GET("geo/1.0/direct")
	suspend fun getCoordinatesForCity(
		@Query("q") city: String
	
	): Response<List<CityCoordinatesResponse>>
}