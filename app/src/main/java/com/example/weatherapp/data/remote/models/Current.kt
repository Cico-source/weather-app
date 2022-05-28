package com.example.weatherapp.data.remote.models


import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("temp")
    val temp: Double
)