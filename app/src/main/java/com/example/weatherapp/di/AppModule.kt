package com.example.weatherapp.di


import com.example.weatherapp.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule
{
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
}