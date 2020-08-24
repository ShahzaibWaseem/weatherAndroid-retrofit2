package com.shahzaib.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class WeatherHistoryAdapter(private var list: MutableList<MutableList<String>>, private val context: Context) :
    RecyclerView.Adapter<WeatherHistoryAdapter.CustomViewHolder>() {

    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeView: TextView = view.findViewById(R.id.threeHourTime)
        val weatherIcon: ImageView = view.findViewById(R.id.threeHourIcon)
        val temperatureView: TextView = view.findViewById(R.id.threeHourTemperature)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: CustomViewHolder, position: Int) {
        holder.timeView.text = list[position][0]
        DownloadImageTask(holder.weatherIcon).execute(list[position][1])
        holder.temperatureView.text = list[position][2] + " °C"
    }

    override fun getItemCount() = list.size
}