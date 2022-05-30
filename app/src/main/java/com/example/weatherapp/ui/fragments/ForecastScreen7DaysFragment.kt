package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.Days7Adapter
import com.example.weatherapp.data.remote.models.Days7
import com.example.weatherapp.databinding.FragmentForecastScreen7DaysBinding

class ForecastScreen7DaysFragment : Fragment(R.layout.fragment_forecast_screen7_days)
{
	
	private var _binding: FragmentForecastScreen7DaysBinding? = null
	private val binding: FragmentForecastScreen7DaysBinding
		get() = _binding!!
	
	private var languageList = ArrayList<Days7>()
	private lateinit var rvAdapter: Days7Adapter
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen7DaysBinding.bind(view)
		
		binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
		
		rvAdapter = Days7Adapter(languageList)
		binding.rvList.adapter = rvAdapter
		
		val language1 = Days7(
			"Java",
			"Java is an Object Oriented Programming language." +
					" Java is used in all kind of applications like Mobile Applications (Android is Java based), " +
					"desktop applications, web applications, client server applications, enterprise applications and many more. ",
			false
		)
		val language2 = Days7(
			"Kotlin",
			"Kotlin is a statically typed, general-purpose programming language" +
					" developed by JetBrains, that has built world-class IDEs like IntelliJ IDEA, PhpStorm, Appcode, etc.",
			false
		)
		val language3 = Days7(
			"Python",
			"Python is a high-level, general-purpose and a very popular programming language." +
					" Python programming language (latest Python 3) is being used in web development, Machine Learning applications, " +
					"along with all cutting edge technology in Software Industry.",
			false
		)
		val language4 = Days7(
			"CPP",
			"C++ is a general purpose programming language and widely used now a days for " +
					"competitive programming. It has imperative, object-oriented and generic programming features. ",
			false
		)
		
		// add items to list
		languageList.add(language1)
		languageList.add(language2)
		languageList.add(language3)
		languageList.add(language4)
		
		rvAdapter.notifyDataSetChanged()
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}