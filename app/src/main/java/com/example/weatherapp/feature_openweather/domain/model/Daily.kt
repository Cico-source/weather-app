package com.example.weatherapp.feature_openweather.domain.model

data class Daily(
	val sunrise: Int,
	val sunset: Int,
	val tempDto: Temp,
	val pressure: Int,
	val humidity: Int,
	val windSpeed: Double,
	val weatherDto: List<Weather>,
	val pop: Double,
	val rain: Double,
	val clouds: Int,
	
	var expand: Boolean = false
)