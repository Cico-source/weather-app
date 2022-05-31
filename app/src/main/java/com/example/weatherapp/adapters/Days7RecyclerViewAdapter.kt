package com.example.weatherapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.data.remote.models.Daily
import com.example.weatherapp.databinding.SingleItemBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt

fun main()
{
	val isoFormat = SimpleDateFormat("HH:mm a", Locale.getDefault())
	isoFormat.timeZone = TimeZone.getTimeZone("GMT")
	
	println(isoFormat.format(Date(1653966631*1000L)))
	println(isoFormat.format(Date(1654022239*1000L)))
}

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
					
					val cal = Calendar.getInstance()
					cal.add(Calendar.DAY_OF_MONTH, position)
					
					val date = cal.time
					
					SimpleDateFormat("E", Locale.getDefault()).format(date).uppercase()
				}
				
				binding.date.text = run {
					
					val cal = Calendar.getInstance()
					cal.add(Calendar.DAY_OF_MONTH, position)
					
					val date = cal.time
					
					SimpleDateFormat("dd.MM.", Locale.getDefault()).format(date).uppercase()
				}
				
				Glide.with(binding.forecastImage.context)
					.load("https://openweathermap.org/img/wn/${this.weather.last().icon}.png")
					.into(binding.forecastImage)
				
				binding.minValue.text = holder.itemView.context.getString(R.string.min_max_forecast_value, this.temp.min.roundToInt())
				binding.maxValue.text = holder.itemView.context.getString(R.string.min_max_forecast_value, this.temp.max.roundToInt())
				binding.rainPercent.text = holder.itemView.context.getString(R.string.rain_percent_forecast_value, this.pop*100, "%")
				binding.description.text = this.weather.last().description
				
				binding.rainValue.text = this.rain.toString()
				binding.humidityValue.text = this.humidity.toString()
				binding.pressureValue.text = this.pressure.toString()
				
				binding.windValue.text = this.windSpeed.toString()
				binding.sunriseValue.text = run {
					
					val cal = Calendar.getInstance()
					cal.timeInMillis = this.sunrise.toLong() * 1000

					val date = cal.time

					SimpleDateFormat("KK:mm a", Locale.getDefault()).format(date).uppercase()
				}
				binding.sunsetValue.text = run {
					
					val cal = Calendar.getInstance()
					cal.timeInMillis = this.sunset.toLong() * 1000
					
					val date = cal.time
					
					SimpleDateFormat("KK:mm a", Locale.getDefault()).format(date).uppercase()
				}
				
				binding.morningValue.text = this.temp.morn.roundToInt().toString()
				binding.afternoonValue.text = this.temp.afternoon.roundToInt().toString()
				binding.eveningValue.text = this.temp.eve.roundToInt().toString()
				binding.nightValue.text = this.temp.night.roundToInt().toString()
				
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