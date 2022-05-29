package com.example.weatherapp.di


import android.content.Context
import com.example.weatherapp.data.remote.api.OpenWeatherApi
import com.example.weatherapp.repository.DefaultOpenWeatherRepository
import com.example.weatherapp.repository.OpenWeatherRepository
import com.example.weatherapp.util.Constants.API_KEY
import com.example.weatherapp.util.Constants.BASE_URL
import com.example.weatherapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
	@Singleton
	@Provides
	fun provideApplicationContext(
		@ApplicationContext context: Context
	) = context
	
	@Singleton
	@Provides
	fun provideOpenWeatherRepository(
		openWeatherApi: OpenWeatherApi,
		@ApplicationContext context: Context
	): OpenWeatherRepository = DefaultOpenWeatherRepository(openWeatherApi, context)
	
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
	
	
}