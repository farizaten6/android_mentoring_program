package com.example.androidmentoringprogram

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewSongsAdapter(val songsNames: List<String>) : RecyclerView.Adapter<RecyclerViewSongsAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var songItem: TextView = item.findViewById(R.id.rcViewSongsItemText)
        private lateinit var songItemName: String

        init {
            item.setOnClickListener {
                songItem.text.let {
                    songItemName = it.toString().substringAfter('-')
                }
                val serviceIntent = Intent(item.context, PlayerService::class.java)
                serviceIntent.putExtra(PlayerService.INTENT_ACTION_NAME, songItemName)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ContextCompat.startForegroundService(item.context, serviceIntent)
                } else {
                    item.context.startService(serviceIntent)
                }

                val activityIntent = Intent(item.context, PlayerActivity::class.java)
                item.context.startActivity(activityIntent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_songs_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.songItem.text = songsNames.get(position)
    }

    override fun getItemCount(): Int {
        return songsNames.size
    }

}