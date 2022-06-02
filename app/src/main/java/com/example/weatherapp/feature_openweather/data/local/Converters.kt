package com.example.weatherapp.feature_openweather.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.weatherapp.feature_openweather.data.util.JsonParser
import com.example.weatherapp.feature_openweather.domain.model.Current
import com.example.weatherapp.feature_openweather.domain.model.Daily
import com.example.weatherapp.feature_openweather.domain.model.Hourly
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
	private val jsonParser: JsonParser
)
{
	
	@TypeConverter
	fun fromCurrentJson(json: String): Current
	{
		return jsonParser.fromJson<Current>(
			json,
			object : TypeToken<Current>()
			{}.type
		) ?: Current(999.0)
	}
	
	@TypeConverter
	fun toCurrentJson(current: Current): String
	{
		return jsonParser.toJson(
			current,
			object : TypeToken<Current>()
			{}.type
		) ?: "{}"
	}
	
	@TypeConverter
	fun fromDailyJson(json: String): List<Daily>
	{
		return jsonParser.fromJson<ArrayList<Daily>>(
			json,
			object : TypeToken<ArrayList<Daily>>()
			{}.type
		) ?: emptyList()
	}
	
	@TypeConverter
	fun toDailyJson(days7: List<Daily>): String
	{
		return jsonParser.toJson(
			days7,
			object : TypeToken<ArrayList<Daily>>()
			{}.type
		) ?: "[]"
	}
	
	@TypeConverter
	fun fromHourlyJson(json: String): List<Hourly>
	{
		return jsonParser.fromJson<ArrayList<Hourly>>(
			json,
			object : TypeToken<ArrayList<Hourly>>()
			{}.type
		) ?: emptyList()
	}
	
	@TypeConverter
	fun toHourlyJson(days7: List<Hourly>): String
	{
		return jsonParser.toJson(
			days7,
			object : TypeToken<ArrayList<Hourly>>()
			{}.type
		) ?: "[]"
	}
}