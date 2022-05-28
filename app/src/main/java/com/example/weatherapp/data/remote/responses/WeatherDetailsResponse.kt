package com.example.weatherapp.data.remote.responses


import com.example.weatherapp.data.remote.models.Current
import com.example.weatherapp.data.remote.models.Daily
import com.google.gson.annotations.SerializedName

data class WeatherDetailsResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("daily")
    val daily: List<Daily>
)