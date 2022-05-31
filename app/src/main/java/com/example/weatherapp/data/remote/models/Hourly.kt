package com.example.weatherapp.data.remote.models

import com.google.gson.annotations.SerializedName

data class Hourly(
	
	@SerializedName("temp")
	val temp: Double,
	@SerializedName("pressure")
	val pressure: Int,
	@SerializedName("humidity")
	val humidity: Int,
	@SerializedName("uvi")
	val uv: Double,
	@SerializedName("clouds")
	val clouds: Int,
	@SerializedName("visibility")
	val visibility: Int,
	@SerializedName("wind_speed")
	val windSpeed: Double,
	@SerializedName("weather")
	val weather: List<Weather>,
	@SerializedName("pop")
	val pop: Double,
	
	var expand: Boolean = false
)
