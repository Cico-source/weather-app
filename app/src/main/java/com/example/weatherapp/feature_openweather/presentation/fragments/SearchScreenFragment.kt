package com.example.weatherapp.feature_openweather.presentation.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DialogSearchableSpinnerBinding
import com.example.weatherapp.databinding.FragmentSearchScreenBinding
import com.example.weatherapp.feature_openweather.presentation.viewmodels.SearchScreenViewModel
import com.example.weatherapp.common.util.Constants
import com.example.weatherapp.common.util.snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen)
{

	private var _binding: FragmentSearchScreenBinding? = null
	private val binding: FragmentSearchScreenBinding
		get() = _binding!!

	private var _dialogBinding: DialogSearchableSpinnerBinding? = null
	private val dialogBinding: DialogSearchableSpinnerBinding
		get() = _dialogBinding!!


	private lateinit var dialog: Dialog

	private val viewModel: SearchScreenViewModel by viewModels()

	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)

		_binding = FragmentSearchScreenBinding.bind(view)
		_dialogBinding = DialogSearchableSpinnerBinding.inflate(layoutInflater, requireView().parent as ViewGroup, false)

		subscribeToObservers()
		listenToEvents()

		val adapter = ArrayAdapter(requireActivity(), android.R.layout.simple_list_item_1, Constants.cities.keys.toList())

		binding.searchTextView.setOnClickListener {

			if (dialogBinding.root.parent == null)
			{
				dialog = Dialog(requireActivity())
				dialog.setContentView(dialogBinding.root)
			}

			dialog.window?.setLayout(800, 900)
			dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
			dialog.show()

			dialogBinding.listView.adapter = adapter

			dialogBinding.inputCityEditText.addTextChangedListener(object : TextWatcher
			{
				override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int)
				{

				}

				override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int)
				{
					adapter.filter.filter(s)
				}

				override fun afterTextChanged(s: Editable?)
				{

				}
			})

			dialogBinding.listView.setOnItemClickListener { _, _, position, _ ->
				
				val selectedCity = adapter.getItem(position)!!

				binding.searchTextView.text = selectedCity

				dialog.dismiss()
				
				viewModel.getWeatherDetailsForCity(selectedCity)
			}
		}

	}

	private fun listenToEvents() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {

		viewModel.setupEvent.collect { event ->
			when (event)
			{
				is SearchScreenViewModel.SetupEvent.GetCityWeatherDetailsErrorEvent ->
				{
					binding.loadingSpinner.isVisible = false
					snackbar(event.error)
				}
				else                                                              ->
				{
					Unit
				}
			}
		}
		
	}

	private fun subscribeToObservers() = viewLifecycleOwner.lifecycleScope.launchWhenStarted {
		
		viewModel.screen.collect { event ->
			when (event)
			{
				is SearchScreenViewModel.SetupEvent.GetCityWeatherDetailsEvent ->
				{
					event.weatherDetails.run {
						binding.apply {
							cityTextView.text = event.city
							humidityTextView.text = getString(R.string.humidity_value, dailyDto[0].humidity, "%")
							cloudsTextView.text = getString(R.string.clouds_value, dailyDto[0].clouds, "%")
							tempTextView.text = getString(R.string.temp_value, currentDto.temp, "°C")
							minTextView.text = getString(R.string.min_value, dailyDto[0].tempDto.min, "°C")
							maxTextView.text = getString(R.string.max_value, dailyDto[0].tempDto.max, "°C")
						}
					}

					binding.loadingSpinner.isVisible = false
					binding.card.isVisible = true
				}
				is SearchScreenViewModel.SetupEvent.MainScreenLoadingEvent     ->
				{
					binding.card.isVisible = false
					binding.loadingSpinner.isVisible = true
				}

				else                                                         ->
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