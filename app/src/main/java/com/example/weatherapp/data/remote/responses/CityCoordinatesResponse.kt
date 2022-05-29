package com.example.weatherapp.data.remote.responses


import com.google.gson.annotations.SerializedName

data class CityCoordinatesResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("country")
    var country: String
)