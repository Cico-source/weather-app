package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.weatherapp.data.remote.models.Daily
import com.example.weatherapp.databinding.SingleItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*
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
				
				binding.dayOfWeek.text = run {
					val todayDate = Calendar.getInstance().time
					
					SimpleDateFormat("E", Locale.getDefault()).format(todayDate).uppercase()
				}
				
				binding.date.text = run {
					
					val todayDate = Calendar.getInstance().time
					
					SimpleDateFormat("d.MM.", Locale.getDefault()).format(todayDate).uppercase()
				}
				
				Glide.with(binding.forecastImage.context)
					.load("https://openweathermap.org/img/wn/${this.weather.last().icon}.png")
					.into(binding.forecastImage)
				
				binding.minValue.text = this.temp.min.toString()
				binding.maxValue.text = this.temp.max.toString()
				binding.rainPercent.text = this.pop.toString()
				binding.description.text = this.weather.last().description
				
				binding.rainValue.text = this.rain.toString()
				binding.humidityValue.text = this.humidity.toString()
				binding.pressureValue.text = this.pressure.toString()
				
				binding.windValue.text = this.windSpeed.toString()
				binding.sunriseValue.text = this.sunrise.toString()
				binding.sunsetValue.text = this.sunset.toString()
				
				binding.morningValue.text = this.temp.morn.toString()
				binding.afternoonValue.text = this.temp.afternoon.toString()
				binding.eveningValue.text = this.temp.eve.toString()
				binding.nightValue.text = this.temp.night.toString()
				
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