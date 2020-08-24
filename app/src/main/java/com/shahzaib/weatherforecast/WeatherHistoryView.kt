package com.shahzaib.weatherforecast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView

class WeatherHistoryAdapter(private var list: List<String>, private val context: Context) :
    RecyclerView.Adapter<WeatherHistoryAdapter.CustomViewHolder>() {

    class CustomViewHolder(val view: View) : RecyclerView.ViewHolder(view)
    {
        val textView = view.findViewById<TextView>(R.id.textValue)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: CustomViewHolder, position: Int) {
        holder.textView.text = list[position]
    }

    override fun getItemCount() = list.size
}