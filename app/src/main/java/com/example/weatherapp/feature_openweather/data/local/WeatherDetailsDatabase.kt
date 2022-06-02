package com.example.weatherapp.feature_openweather.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherapp.feature_openweather.data.local.entity.WeatherDetailsEntity


@Database(
	entities = [WeatherDetailsEntity::class],
	version = 1
)
@TypeConverters(Converters::class)
abstract class WeatherDetailsDatabase : RoomDatabase()
{
	
	abstract val dao: WeatherDetailsDao
}
