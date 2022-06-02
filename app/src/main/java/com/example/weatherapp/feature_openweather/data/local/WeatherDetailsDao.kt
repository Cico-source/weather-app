package com.example.weatherapp.feature_openweather.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.feature_openweather.data.local.entity.WeatherDetailsEntity

@Dao
interface WeatherDetailsDao
{
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertWeatherDetails(weatherDetails: WeatherDetailsEntity)
	
	@Query("DELETE FROM weatherdetailsentity")
	suspend fun deleteWeatherDetails()
	
	@Query("SELECT * FROM weatherdetailsentity")
	suspend fun getWeatherDetails(): WeatherDetailsEntity?
	
}