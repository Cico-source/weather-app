package com.example.weatherapp.data.remote.api

import com.example.weatherapp.data.remote.responses.CountryCoordinatesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface OpenWeatherApi
{
	@GET("geo/1.0/zip")
	suspend fun getCoordinatesByZipAndCountryCode(
		@Query("zip") zipAndCountryCode: String
	
	): Response<CountryCoordinatesResponse>
}