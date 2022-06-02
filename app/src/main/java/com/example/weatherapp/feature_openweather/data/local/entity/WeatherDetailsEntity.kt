package com.example.weatherapp.feature_openweather.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weatherapp.feature_openweather.domain.model.Current
import com.example.weatherapp.feature_openweather.domain.model.Daily
import com.example.weatherapp.feature_openweather.domain.model.Hourly
import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails

@Entity
data class WeatherDetailsEntity(
	val currentDto: Current,
	val dailyDto: List<Daily>,
	val hourlyDto: List<Hourly>,
	val date: Long = System.currentTimeMillis(),
	@PrimaryKey val id: Int? = null
)
{
	
	fun toWeatherDetails(): WeatherDetails
	{
		return WeatherDetails(
			currentDto = currentDto,
			dailyDto = dailyDto,
			hourlyDto = hourlyDto,
		)
	}
	
}