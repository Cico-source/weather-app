package com.example.weatherapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentMainScreenBinding
import com.example.weatherapp.databinding.FragmentSearchScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchScreenFragment : Fragment(R.layout.fragment_search_screen)
{
	
	private var _binding: FragmentSearchScreenBinding? = null
	private val binding: FragmentSearchScreenBinding
		get() = _binding!!
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		_binding = FragmentSearchScreenBinding.bind(view)
	}
	
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}