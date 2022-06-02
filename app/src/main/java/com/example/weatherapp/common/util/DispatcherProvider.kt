package com.example.weatherapp.common.util

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider
{
	val main: CoroutineDispatcher
	val io: CoroutineDispatcher
	val default: CoroutineDispatcher
}