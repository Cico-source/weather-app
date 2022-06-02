package com.example.weatherapp.feature_openweather.domain.model

data class WeatherDetails(
	val currentDto: Current,
	val dailyDto: List<Daily>,
	val hourlyDto: List<Hourly>
)