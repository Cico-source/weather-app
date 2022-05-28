package com.example.weatherapp.data.remote.models


import com.google.gson.annotations.SerializedName

data class Daily(
    @SerializedName("temp")
    val temp: Temp,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("clouds")
    val clouds: Int
)