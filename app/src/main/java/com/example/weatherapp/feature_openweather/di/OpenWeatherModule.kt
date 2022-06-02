package com.example.weatherapp.feature_openweather.di


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.weatherapp.feature_openweather.data.local.Converters
import com.example.weatherapp.feature_openweather.data.local.WeatherDetailsDao
import com.example.weatherapp.feature_openweather.data.local.WeatherDetailsDatabase
import com.example.weatherapp.feature_openweather.data.remote.api.OpenWeatherApi
import com.example.weatherapp.feature_openweather.data.remote.api.OpenWeatherApi.Companion.API_KEY
import com.example.weatherapp.feature_openweather.data.remote.api.OpenWeatherApi.Companion.BASE_URL
import com.example.weatherapp.feature_openweather.data.repository.OpenWeatherRepositoryImpl
import com.example.weatherapp.feature_openweather.data.util.GsonParser
import com.example.weatherapp.feature_openweather.domain.repository.OpenWeatherRepository
import com.example.weatherapp.feature_openweather.domain.use_case.GetCityCoordinates
import com.example.weatherapp.feature_openweather.domain.use_case.GetWeatherDetails
import com.example.weatherapp.common.util.DispatcherProvider
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OpenWeatherModule
{
	
	@Singleton
	@Provides
	fun provideApplicationContext(
		@ApplicationContext context: Context
	) = context
	
	@Provides
	@Singleton
	fun provideWeatherDetailsDatabase(app: Application): WeatherDetailsDatabase
	{
		return Room.databaseBuilder(
			app, WeatherDetailsDatabase::class.java, "weather_db"
		)
			.addTypeConverter(Converters(GsonParser(Gson())))
			.build()
	}
	
	@Provides
	@Singleton
	fun provideWeatherDetailsDao(db: WeatherDetailsDatabase): WeatherDetailsDao
	{
		return db.dao
	}
	
	@Singleton
	@Provides
	fun provideOpenWeatherRepository(
		dao: WeatherDetailsDao,
		openWeatherApi: OpenWeatherApi,
		@ApplicationContext context: Context
	): OpenWeatherRepository = OpenWeatherRepositoryImpl(openWeatherApi, context, dao)
	
	@Singleton
	@Provides
	fun provideDispatcherProvider(): DispatcherProvider
	{
		return object : DispatcherProvider
		{
			override val main: CoroutineDispatcher
				get() = Dispatchers.Main
			override val io: CoroutineDispatcher
				get() = Dispatchers.IO
			override val default: CoroutineDispatcher
				get() = Dispatchers.Default
		}
	}
	
	@Singleton
	@Provides
	fun provideOkHttpClient(): OkHttpClient
	{
		return OkHttpClient.Builder()
			.addInterceptor { chain ->
				val url = chain.request().url.newBuilder()
					.addQueryParameter("appid", API_KEY)
					.build()
				val request = chain.request().newBuilder()
					.url(url)
					.build()
				chain.proceed(request)
			}
			.addInterceptor(HttpLoggingInterceptor().apply {
				level = HttpLoggingInterceptor.Level.BODY
			})
			.build()
	}
	
	@Singleton
	@Provides
	fun provideOpenWeatherApi(okHttpClient: OkHttpClient): OpenWeatherApi
	{
		return Retrofit.Builder()
			.addConverterFactory(GsonConverterFactory.create())
			.baseUrl(BASE_URL)
			.client(okHttpClient)
			.build()
			.create(OpenWeatherApi::class.java)
	}
	
	@Provides
	@Singleton
	fun provideGetCityCoordinatesUseCase(repository: OpenWeatherRepository): GetCityCoordinates
	{
		return GetCityCoordinates(repository)
	}
	
	@Provides
	@Singleton
	fun provideGetWeatherDetailsUseCase(repository: OpenWeatherRepository): GetWeatherDetails
	{
		return GetWeatherDetails(repository)
	}
	
}