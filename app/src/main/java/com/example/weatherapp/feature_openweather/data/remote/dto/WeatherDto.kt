package com.example.weatherapp.feature_openweather.data.remote.dto


import com.example.weatherapp.feature_openweather.domain.model.Weather
import com.google.gson.annotations.SerializedName

data class WeatherDto(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String
)
{
    
    fun toWeather(): Weather
    {
        return Weather(
            description = description,
            icon = icon
        )
    }
    
}