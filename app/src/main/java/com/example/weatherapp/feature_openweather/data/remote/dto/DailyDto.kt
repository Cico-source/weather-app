package com.example.weatherapp.feature_openweather.data.remote.dto


import com.example.weatherapp.feature_openweather.domain.model.Daily
import com.google.gson.annotations.SerializedName

data class DailyDto(
	@SerializedName("sunrise")
	val sunrise: Int,
	@SerializedName("sunset")
	val sunset: Int,
	@SerializedName("temp")
	val tempDto: TempDto,
	@SerializedName("pressure")
	val pressure: Int,
	@SerializedName("humidity")
	val humidity: Int,
	@SerializedName("wind_speed")
	val windSpeed: Double,
	@SerializedName("weather")
	val weatherDto: List<WeatherDto>,
	@SerializedName("pop")
	val pop: Double,
	@SerializedName("rain")
	val rain: Double,
	@SerializedName("clouds")
	val clouds: Int,
	
	var expand: Boolean = false
)
{
	
	fun toDaily(): Daily
	{
		return Daily(
			sunrise = sunrise,
			sunset = sunset,
			tempDto = tempDto.toTemp(),
			pressure = pressure,
			humidity = humidity,
			windSpeed = windSpeed,
			weatherDto = weatherDto.map { it.toWeather() },
			pop = pop,
			rain = rain,
			clouds = clouds,
			expand = expand
			)
	}
}