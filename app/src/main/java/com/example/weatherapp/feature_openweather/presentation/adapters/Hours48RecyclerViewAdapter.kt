package com.example.weatherapp.feature_openweather.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.Hours48ItemBinding
import com.example.weatherapp.feature_openweather.domain.model.Hourly
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.roundToInt


class Hours48RecyclerViewAdapter @Inject constructor() : RecyclerView.Adapter<Hours48RecyclerViewAdapter.ViewHolder>()
{
    
    inner class ViewHolder(val binding: Hours48ItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        
        init
        {
            binding.cardLayout.setOnClickListener {
                
                hours48[adapterPosition].apply {
                    
                    this.expand = !this.expand
                    
                    notifyItemChanged(adapterPosition)
                    
                    if (expand)
                    {
                        onItemClickListener?.let { click ->
                            
                            click(adapterPosition)
                        }
                    }
                }
                
            }
        }
        
    }
    
    var hours48 = listOf<Hourly>()
        private set
    
    private var onItemClickListener: ((Int) -> Unit)? = null
    
    
    fun setOnItemClickListener(listener: (Int) -> Unit)
    {
        onItemClickListener = listener
    }
    
    suspend fun updateDataset(newDataset: List<Hourly>) = withContext(Dispatchers.Default) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback()
        {
            override fun getOldListSize(): Int
            {
                return hours48.size
            }
            
            override fun getNewListSize(): Int
            {
                return newDataset.size
            }
            
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return hours48[oldItemPosition] == newDataset[newItemPosition]
            }
            
            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
            {
                return hours48[oldItemPosition] == newDataset[newItemPosition]
            }
        })
        
        withContext(Dispatchers.Main) {
            
            hours48 = newDataset
            diff.dispatchUpdatesTo(this@Hours48RecyclerViewAdapter)
        }
        
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Hours48RecyclerViewAdapter.ViewHolder
    {
        return ViewHolder(Hours48ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    
    override fun onBindViewHolder(holder: Hours48RecyclerViewAdapter.ViewHolder, position: Int)
	{
		with(holder) {
			with(hours48[position]) {
				
				val cal = Calendar.getInstance()
				cal.add(Calendar.HOUR_OF_DAY, position)
				
				val date = cal.time
				
				binding.hour.text = SimpleDateFormat("HH:00", Locale.getDefault()).format(date).uppercase()
				
				binding.date.text = SimpleDateFormat("dd.MM.", Locale.getDefault()).format(date).uppercase()
				
				
				Glide.with(binding.forecastImage.context)
					.load("https://openweathermap.org/img/wn/${this.weatherDto.last().icon}.png")
					.into(binding.forecastImage)
			
				binding.tempValue.text = holder.itemView.context.getString(R.string.min_max_forecast_value, this.temp.roundToInt())
				binding.rainPercent.text = holder.itemView.context.getString(R.string.rain_percent_forecast_value, this.pop * 100, "%")
				binding.description.text = this.weatherDto.last().description
			
				binding.cloudsValue.text = this.clouds.toString()
				binding.humidityValue.text = this.humidity.toString()
				binding.pressureValue.text = this.pressure.toString()
			
				binding.windValue.text = this.windSpeed.toString()
				binding.visibilityValue.text = (this.visibility / 1000).toString()
				binding.uvValue.text = this.uv.toString()
			
				binding.expandedView.visibility = if (this.expand) View.VISIBLE else View.GONE
			}
		}
	}
    
    override fun getItemCount(): Int
    {
        return hours48.size
    }
}