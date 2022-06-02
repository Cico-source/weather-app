package com.example.weatherapp.feature_openweather.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ForecastPagerAdapter(fr: FragmentActivity) : FragmentStateAdapter(fr)
{
	
	private val fragmentList = ArrayList<Fragment>()
	
	
	fun addFragment(fragment: Fragment)
	{
		fragmentList.add(fragment)
	}
	
	override fun getItemCount(): Int
	{
		return fragmentList.size
	}
	
	override fun createFragment(position: Int): Fragment
	{
		return fragmentList[position]
	}
	
}