package com.example.weatherapp.common.util

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

class SmoothScrolling(private val context: Context, layoutDirection: Int) :
	LinearLayoutManager(context, layoutDirection, false)
{
	
	companion object
	{
		private const val MILLISECONDS_PER_INCH = 200f
	}
	
	override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State, position: Int)
	{
		val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context)
		{
			
			override fun getVerticalSnapPreference(): Int = SNAP_TO_START
			
			override fun getHorizontalSnapPreference(): Int = SNAP_TO_START
			
			override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float
			{
				return MILLISECONDS_PER_INCH / displayMetrics.densityDpi
			}
		}
		
		smoothScroller.targetPosition = position
		startSmoothScroll(smoothScroller)
	}
}