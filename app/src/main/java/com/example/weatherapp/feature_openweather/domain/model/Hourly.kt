package com.example.weatherapp.feature_openweather.domain.model

data class Hourly(
	val temp: Double,
	val pressure: Int,
	val humidity: Int,
	val uv: Double,
	val clouds: Int,
	val visibility: Int,
	val windSpeed: Double,
	val weatherDto: List<Weather>,
	val pop: Double,
	
	var expand: Boolean = false
)