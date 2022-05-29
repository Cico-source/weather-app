package com.example.weatherapp.ui.viewmodels

import android.app.Dialog
import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.weatherapp.repository.OpenWeatherRepository
import com.example.weatherapp.util.DispatcherProvider
import javax.inject.Inject

class SearchScreenViewModel @Inject constructor(
	private val repository: OpenWeatherRepository,
	private val dispatchers: DispatcherProvider,
	
) : ViewModel()
{
	

}