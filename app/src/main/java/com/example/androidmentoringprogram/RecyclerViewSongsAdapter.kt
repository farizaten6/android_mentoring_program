package com.example.androidmentoringprogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSongsAdapter(val songsNames: List<String>) : RecyclerView.Adapter<RecyclerViewSongsAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var itemName: TextView = item.findViewById(R.id.rcViewSongsItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_songs_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemName.text = songsNames.get(position)
    }

    override fun getItemCount(): Int {
        return songsNames.size
    }

}