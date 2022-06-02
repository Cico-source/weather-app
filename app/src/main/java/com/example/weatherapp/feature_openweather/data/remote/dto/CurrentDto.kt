package com.example.weatherapp.feature_openweather.data.remote.dto


import com.example.weatherapp.feature_openweather.domain.model.Current
import com.google.gson.annotations.SerializedName

data class CurrentDto(
	@SerializedName("temp")
	val temp: Double
)
{
	
	fun toCurrent(): Current
	{
		return Current(
			temp = temp
		)
	}
	
}