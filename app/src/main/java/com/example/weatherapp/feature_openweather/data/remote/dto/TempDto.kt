package com.example.weatherapp.feature_openweather.data.remote.dto


import com.example.weatherapp.feature_openweather.domain.model.Temp
import com.google.gson.annotations.SerializedName

data class TempDto(
	@SerializedName("day")
	val afternoon: Double,
	@SerializedName("min")
	val min: Double,
	@SerializedName("max")
	val max: Double,
	@SerializedName("night")
	val night: Double,
	@SerializedName("eve")
	val eve: Double,
	@SerializedName("morn")
	val morn: Double
)
{
	
	fun toTemp(): Temp
	{
		return Temp(
			afternoon = afternoon,
			min = min,
			max = max,
			night = night,
			eve = eve,
			morn = morn
		)
		
	}
	
}