package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainScreenBinding
import com.example.weatherapp.ui.viewmodels.MainScreenViewModel
import com.example.weatherapp.util.snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainScreenFragment : Fragment(R.layout.fragment_main_screen)
{
	
	private var _binding: FragmentMainScreenBinding? = null
	private val binding: FragmentMainScreenBinding
		get() = _binding!!
	
	private val viewModel: MainScreenViewModel by viewModels()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentMainScreenBinding.bind(view)
		
		subscribeToObservers()
		listenToEvents()
		
		viewModel.getCoordinatesForCity("City of Zagreb")
		
	}
	
	private fun listenToEvents() = lifecycleScope.launchWhenStarted {
		
		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is MainScreenViewModel.SetupEvent.GetCityCoordsErrorEvent ->
				{
					snackbar(event.error)
				}
				else                                                      ->
				{
					Unit
				}
			}
		}
	}
	
	private fun subscribeToObservers() = lifecycleScope.launchWhenStarted {
		viewModel.screen.collect { event ->
			when (event)
			{
				is MainScreenViewModel.SetupEvent.GetCityCoordsEvent     ->
				{
					event.coords.run{
						Log.i("FFF", "${this[0].lat}, ${this[0].lon}")
					}
				}
				is MainScreenViewModel.SetupEvent.MainScreenLoadingEvent ->
				{
//					binding.ProgressBar.isVisible = true
				}
				
				else                                                     ->
				{
					Unit
				}
			}
		}
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}