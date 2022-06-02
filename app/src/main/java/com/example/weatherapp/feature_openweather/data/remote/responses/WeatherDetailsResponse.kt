package com.example.weatherapp.feature_openweather.data.remote.responses


import com.example.weatherapp.feature_openweather.data.local.entity.WeatherDetailsEntity
import com.example.weatherapp.feature_openweather.data.remote.dto.CurrentDto
import com.example.weatherapp.feature_openweather.data.remote.dto.DailyDto
import com.example.weatherapp.feature_openweather.data.remote.dto.HourlyDto
import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails
import com.google.gson.annotations.SerializedName

data class WeatherDetailsResponse(
	@SerializedName("current")
	val currentDto: CurrentDto,
	@SerializedName("daily")
	val dailyDto: List<DailyDto>,
	@SerializedName("hourly")
	val hourlyDto: List<HourlyDto>

)
{
	
	fun toWeatherDetails(): WeatherDetails
	{
		return WeatherDetails(
			currentDto = currentDto.toCurrent(),
			dailyDto = dailyDto.map { it.toDaily() },
			hourlyDto = hourlyDto.map { it.toHourly() }
		)
	}
	
	fun toWeatherDetailsEntity(): WeatherDetailsEntity
	{
		return WeatherDetailsEntity(
			currentDto = currentDto.toCurrent(),
			dailyDto = dailyDto.map { it.toDaily() },
			hourlyDto = hourlyDto.map { it.toHourly() }
		)
	}

	
}