package com.example.weatherapp.data.remote.models


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("feels_like")
    val feelsLike: FeelsLike,
    @SerializedName("pressure")
    val pressure: Int,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("wind_speed")
    val windSpeed: Double,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("pop")
    val pop: Double,
    @SerializedName("rain")
    val rain: Double,
    @SerializedName("clouds")
    val clouds: Int,

    var expand: Boolean = false
)