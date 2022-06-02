package com.example.weatherapp.feature_openweather.data.remote.responses


import com.google.gson.annotations.SerializedName

data class CityCoordinatesResponse(
	@SerializedName("lat")
	val lat: Double,
	@SerializedName("lon")
	val lon: Double,
	@SerializedName("country")
	var country: String
)
{
	
//	fun toCityCoordinatesEntity(): CityCoordinatesEntity
//	{
//		return CityCoordinatesEntity(
//			lat = lat,
//			lon = lon,
//			country = country
//		)
//	}
	
}