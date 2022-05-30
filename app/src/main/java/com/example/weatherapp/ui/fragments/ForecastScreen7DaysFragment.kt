package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.adapters.Days7RecyclerViewAdapter
import com.example.weatherapp.data.remote.models.Days7
import com.example.weatherapp.databinding.FragmentForecastScreen7DaysBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ForecastScreen7DaysFragment : Fragment(R.layout.fragment_forecast_screen7_days)
{
	
	private var _binding: FragmentForecastScreen7DaysBinding? = null
	private val binding: FragmentForecastScreen7DaysBinding
		get() = _binding!!
	
	private var dummyList = ArrayList<Days7>()
	
	@Inject
	lateinit var days7Adapter: Days7RecyclerViewAdapter
	
	private var updateDays7Job: Job? = null
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentForecastScreen7DaysBinding.bind(view)
		
		binding.rvList.layoutManager = LinearLayoutManager(requireActivity())
		binding.rvList.adapter = days7Adapter
		
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
		
		dummyList.add(language1)
		dummyList.add(language2)
		dummyList.add(language3)
		dummyList.add(language4)
		
		updateDays7RecyclerView(dummyList)
	}
	
	private fun updateDays7RecyclerView(players: List<Days7>)
	{
		updateDays7Job?.cancel()
		updateDays7Job = lifecycleScope.launch {
			
			days7Adapter.updateDataset(players)
		}
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}