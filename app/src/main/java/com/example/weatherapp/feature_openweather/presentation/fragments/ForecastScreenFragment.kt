package com.example.weatherapp.feature_openweather.presentation.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.feature_openweather.presentation.adapters.ForecastPagerAdapter
import com.example.weatherapp.databinding.FragmentForecastScreenBinding
import com.example.weatherapp.feature_openweather.presentation.viewmodels.ForecastScreen7DaysViewModel
import com.google.android.material.tabs.TabLayoutMediator

class ForecastScreenFragment : Fragment(R.layout.fragment_forecast_screen)
{
	
	private var _binding: FragmentForecastScreenBinding? = null
	private val binding: FragmentForecastScreenBinding
		get() = _binding!!
	
	lateinit var adapter: ForecastPagerAdapter
	
	private val viewModel: ForecastScreen7DaysViewModel by viewModels()
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreenBinding.bind(view)
		
		setToolbar()
		
		setViewPager()
		connectTabLayoutAndViewPager()
		
		
		
	}
	
	private fun setToolbar()
	{
		(activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
		(activity as AppCompatActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
	}
	
	private fun setViewPager()
	{
		adapter = ForecastPagerAdapter(requireActivity())
		addFragmentsToPagerAdapter()
		
		binding.viewPager.adapter = adapter
	}
	
	private fun addFragmentsToPagerAdapter()
	{
		adapter.addFragment(ForecastScreen48HoursFragment())
		adapter.addFragment(ForecastScreen7DaysFragment())
	}
	
	private fun connectTabLayoutAndViewPager()
	{
		TabLayoutMediator(binding.tabLayout, binding.viewPager) { _, _ -> }.attach()
		
		setTitlesToTabs()
	}
	
	private fun setTitlesToTabs()
	{
		binding.tabLayout
			.getTabAt(0)
			?.text = "48 HOURS"
		binding.tabLayout
			.getTabAt(1)
			?.text = "7 DAYS"
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
	
}