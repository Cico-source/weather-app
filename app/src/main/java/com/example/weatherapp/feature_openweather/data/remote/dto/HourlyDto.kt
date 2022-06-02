package com.example.weatherapp.feature_openweather.data.remote.dto

import com.example.weatherapp.feature_openweather.domain.model.Hourly
import com.google.gson.annotations.SerializedName

data class HourlyDto(
	
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
	val weatherDto: List<WeatherDto>,
	@SerializedName("pop")
	val pop: Double,
	
	var expand: Boolean = false
)
{
	
	fun toHourly(): Hourly
	{
		return Hourly(
			temp = temp,
			pressure = pressure,
			humidity = humidity,
			uv = uv,
			clouds = clouds,
			visibility = visibility,
			windSpeed = windSpeed,
			weatherDto = weatherDto.map { it.toWeather() },
			pop = pop,
			expand = expand
		)
	}
	
}
