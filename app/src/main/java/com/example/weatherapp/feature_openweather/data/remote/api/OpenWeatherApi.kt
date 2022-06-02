package com.example.weatherapp.feature_openweather.data.remote.api

import com.example.weatherapp.feature_openweather.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.feature_openweather.data.remote.responses.WeatherDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi
{
	@GET("geo/1.0/direct")
	suspend fun getCoordinatesForCity(
		@Query("q") city: String,
		@Query("limit") limit: Int = 5
	
	): Response<List<CityCoordinatesResponse>>
	
	@GET("data/3.0/onecall")
	suspend fun getWeatherDetailsByCityCoords(
		@Query("lat") lat: String,
		@Query("lon") lon: String,
		@Query("units") units: String = "metric"
	
	): Response<WeatherDetailsResponse>
	
	companion object {
		const val BASE_URL = "https://api.openweathermap.org/"
		const val API_KEY = ""
	}
}