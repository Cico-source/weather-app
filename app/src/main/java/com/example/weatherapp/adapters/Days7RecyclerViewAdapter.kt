package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.remote.models.Daily
import com.example.weatherapp.databinding.SingleItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Days7RecyclerViewAdapter @Inject constructor(): RecyclerView.Adapter<Days7RecyclerViewAdapter.ViewHolder>()
{
	
	inner class ViewHolder(val binding: SingleItemBinding) : RecyclerView.ViewHolder(binding.root)
	
	var days7 = listOf<Daily>()
		private set
	
	suspend fun updateDataset(newDataset: List<Daily>) = withContext(Dispatchers.Default) {
		val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback()
		{
			override fun getOldListSize(): Int
			{
				return days7.size
			}
			
			override fun getNewListSize(): Int
			{
				return newDataset.size
			}
			
			override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
			{
				return days7[oldItemPosition] == newDataset[newItemPosition]
			}
			
			override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
			{
				return days7[oldItemPosition] == newDataset[newItemPosition]
			}
		})
		
		withContext(Dispatchers.Main) {
			
			days7 = newDataset
			diff.dispatchUpdatesTo(this@Days7RecyclerViewAdapter)
		}
		
	}
	
	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Days7RecyclerViewAdapter.ViewHolder
	{
		return ViewHolder(SingleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
	}
	
	override fun onBindViewHolder(holder: Days7RecyclerViewAdapter.ViewHolder, position: Int)
	{
		with(holder) {
			with(days7[position]) {
				
//				binding.tvLangName.text = this.name
//				binding.tvDescription.text = this.description
//				binding.expandedView.visibility = View.GONE
				
				binding.cardLayout.setOnClickListener {
					
					this.expand = !this.expand
					binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
					
				}
				
			}
		}
	}
	
	override fun getItemCount(): Int
	{
		return days7.size
	}
}