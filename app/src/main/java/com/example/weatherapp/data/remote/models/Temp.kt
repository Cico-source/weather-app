package com.example.weatherapp.data.remote.models


import com.google.gson.annotations.SerializedName

data class Temp(
    @SerializedName("min")
    val min: Double,
    @SerializedName("max")
    val max: Double
)