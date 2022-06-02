package com.example.weatherapp.feature_openweather.domain.model

data class CityCoordinates(
	val lat: Double,
	val lon: Double,
	var country: String
)