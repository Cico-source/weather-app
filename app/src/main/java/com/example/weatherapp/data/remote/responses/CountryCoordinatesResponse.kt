package com.example.weatherapp.data.remote.responses


import com.google.gson.annotations.SerializedName

data class CountryCoordinatesResponse(
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
)