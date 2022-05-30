package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.remote.models.Days7
import com.example.weatherapp.databinding.SingleItemBinding

class Days7Adapter(private var languageList: List<Days7>): RecyclerView.Adapter<Days7Adapter.ViewHolder>()
{
	
	inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Days7Adapter.ViewHolder
	{
		val binding = SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: Days7Adapter.ViewHolder, position: Int)
	{
		with(holder) {
			with(languageList[position]) {
				binding.tvLangName.text = this.name
				// set description to the text
				// since this is inside "expandedView" its visibility will be gone initially
				// after click on the item we will make the visibility of the "expandedView" visible
				// which will also make the visibility of desc also visible
				binding.tvDescription.text = this.description
				// check if boolean property "extend" is true or false
				// if it is true make the "extendedView" Visible
				binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
				// on Click of the item take parent card view in our case
				// revert the boolean "expand"
				binding.cardLayout.setOnClickListener {
					this.expand = !this.expand
					notifyDataSetChanged()
				}
			}
		}
	}
	
	override fun getItemCount(): Int
	{
		return languageList.size
	}
}