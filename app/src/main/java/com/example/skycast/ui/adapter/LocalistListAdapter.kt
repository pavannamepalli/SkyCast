package com.example.skycast.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skycast.R
import com.example.skycast.data.model.searchcities.LocationList

class LocalistListAdapter(private val items: LocationList) :RecyclerView.Adapter<LocalistListAdapter.ViewHolder> (){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.location_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder:ViewHolder, position: Int) {
       val item = items[position]
        holder.localName.text=item.LocalizedName
        holder.countryName.text=  item.AdministrativeArea.LocalizedName+ " , " +item.Country.LocalizedName
    }



    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val localName: TextView = itemView.findViewById(R.id.localisedname)
        val countryName: TextView = itemView.findViewById(R.id.countryName)

    }

}