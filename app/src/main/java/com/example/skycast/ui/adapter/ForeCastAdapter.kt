package com.example.skycast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.R
import com.example.skycast.data.model.dailyforcast.DailyForecast
import com.example.skycast.utils.Utils
import com.squareup.picasso.Picasso

class ForeCastAdapter(private val items: List<DailyForecast>) : RecyclerView.Adapter<ForeCastAdapter.ViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.forcast_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.dateTextView.text = Utils().getDateFromat(item.Date)
        holder.phraseTextView.text = Utils().insertNewLineAfterNWords(item.Day.IconPhrase)
        holder.tempTextView.text= "${Utils().fahrenheitToCelsius(item.Temperature.Maximum.Value) }/${Utils().fahrenheitToCelsius(item.Temperature.Minimum.Value)}"
        Picasso.get()
            .load("https://developer.accuweather.com/sites/default/files/0${item.Day.Icon}-s.png")
            .placeholder(R.drawable.ic_01) // Optional: Set a placeholder image
            .into(holder.imageVIew);
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateText)
        val phraseTextView: TextView = itemView.findViewById(R.id.phraseText)
        val tempTextView: TextView = itemView.findViewById(R.id.tempText)
        val imageVIew : ImageView= itemView.findViewById(R.id.imageView)
    }

}