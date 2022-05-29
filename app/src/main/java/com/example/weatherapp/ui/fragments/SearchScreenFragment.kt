package com.example.weatherapp.ui.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.weatherapp.R
import com.example.weatherapp.databinding.DialogSearchableSpinnerBinding
import com.example.weatherapp.databinding.FragmentSearchScreenBinding
import com.example.weatherapp.util.Constants
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
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?)
	{
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentSearchScreenBinding.bind(view)
		_dialogBinding = DialogSearchableSpinnerBinding.inflate(layoutInflater, requireView().parent as ViewGroup, false)
		
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
				
				binding.searchTextView.text = adapter.getItem(position)
				
				dialog.dismiss()
			}
		}
		
	}
	
	override fun onDestroy()
	{
		super.onDestroy()
		_binding = null
	}
}