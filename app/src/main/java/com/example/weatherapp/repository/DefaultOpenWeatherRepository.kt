package com.example.weatherapp.repository

import android.content.Context
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.api.OpenWeatherApi
import com.example.weatherapp.data.remote.responses.CityCoordinatesResponse
import com.example.weatherapp.data.remote.responses.WeatherDetailsResponse
import com.example.weatherapp.util.Resource
import com.example.weatherapp.util.checkForInternetConnection
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


class DefaultOpenWeatherRepository @Inject constructor(
	private val openWeatherApi: OpenWeatherApi,
	private val context: Context

) : OpenWeatherRepository
{
	override suspend fun getCoordinatesForCity(city: String, limit: Int): Resource<List<CityCoordinatesResponse>>
	{
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
		
		return if (response.isSuccessful && response.body() != null)
		{
			Resource.Success(response.body()!!)
		}
		else
		{
			Resource.Error(context.getString(R.string.error_unknown))
		}
	}
	
	override suspend fun getWeatherDetailsByCityCoords(lat: String, lon: String, units: String): Resource<WeatherDetailsResponse>
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
		
		return if (response.isSuccessful && response.body() != null)
		{
			Resource.Success(response.body()!!)
		}
		else
		{
			Resource.Error(context.getString(R.string.error_unknown))
		}
	}
}