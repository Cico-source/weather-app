package com.example.weatherapp.feature_openweather.data.repository

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.feature_openweather.data.local.WeatherDetailsDao
import com.example.weatherapp.feature_openweather.data.remote.api.OpenWeatherApi
import com.example.weatherapp.feature_openweather.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.feature_openweather.domain.model.WeatherDetails
import com.example.weatherapp.feature_openweather.domain.repository.OpenWeatherRepository
import com.example.weatherapp.common.util.Resource
import com.example.weatherapp.common.util.checkForInternetConnection
import retrofit2.HttpException
import java.io.IOException
import java.sql.Timestamp
import java.util.*
import javax.inject.Inject


class OpenWeatherRepositoryImpl @Inject constructor(
	private val openWeatherApi: OpenWeatherApi,
	private val context: Context,
	private val dao: WeatherDetailsDao

) : OpenWeatherRepository
{
	
	override suspend fun getCoordinatesForCity(city: String, cacheDuration: Int, limit: Int): Resource<List<CityCoordinatesResponse>>
	{
		var weatherDetails = dao.getWeatherDetails()
		
		if (cacheDuration > 0)
		{
			weatherDetails?.let {
				
				val timestamp = Timestamp(it.date)
				val calBaza = Calendar.getInstance()
				calBaza.time = timestamp
				
				val calTrenutno = Calendar.getInstance()
				calTrenutno.add(Calendar.MINUTE, -cacheDuration)
				
				// If cached data is not older than cacheDuration
				if (calTrenutno.time.before(calBaza.time))
				{
					// Load cache
					return Resource.CachingSuccess()
				}
				else
				{
					// Delete old cache
					dao.deleteWeatherDetails()
					weatherDetails = null
				}
			}
			
		}
		
		// No Internet
		if (!context.checkForInternetConnection())
		{
			return Resource.Error(context.getString(R.string.error_internet_turned_off))
		}
		
		val response = try
		{
			openWeatherApi.getCoordinatesForCity(city)
		}
		catch (e: HttpException)
		{
			return Resource.Error(context.getString(R.string.error_http))
		}
		catch (e: IOException)
		{
			return Resource.Error(context.getString(R.string.check_internet_connection))
		}
		
		if (response.isSuccessful && response.body() != null)
		{
			return Resource.Success(response.body()!!)
		}
		else
		{
			return Resource.Error(context.getString(R.string.error_unknown))
		}
		
	}
	
	override suspend fun getWeatherDetailsByCityCoords(lat: String, lon: String, caching: Boolean, units: String): Resource<WeatherDetails>
	{
		if (!context.checkForInternetConnection())
		{
			return Resource.Error(context.getString(R.string.error_internet_turned_off))
		}
		
		val response = try
		{
			openWeatherApi.getWeatherDetailsByCityCoords(lat, lon)
		}
		catch (e: HttpException)
		{
			return Resource.Error(context.getString(R.string.error_http))
		}
		catch (e: IOException)
		{
			return Resource.Error(context.getString(R.string.check_internet_connection))
		}
		
		val body = response.body()
		
		if (response.isSuccessful && body != null)
		{
			if (caching)
			{
				// Remove existing cache
				dao.deleteWeatherDetails()
				
				// Update with new cache
				dao.insertWeatherDetails(body.toWeatherDetailsEntity())
			}
			
			return Resource.Success(body.toWeatherDetails())
		}
		else
		{
			return Resource.Error(context.getString(R.string.error_unknown))
		}
		
	}
}