package com.example.androidmentoringprogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter: RecyclerView.Adapter<RecyclerViewAdapter.RCVHolder>() {
    val figuresList = listOf(
        Figure(R.drawable.triangle, "Triange"),
        Figure(R.drawable.circle, "Circle"),
        Figure(R.drawable.kite, "Kite"),
        Figure(R.drawable.parallelogram, "Parallelogram"),
        Figure(R.drawable.pentagon, "Pentagon"),
        Figure(R.drawable.rectangle, "Rectangle"),
        Figure(R.drawable.rhombus, "Rhombus"),
        Figure(R.drawable.square, "Square"),
        Figure(R.drawable.trapezoid, "Trapezoid"),
        Figure(R.drawable.hexagon, "Hexagon")
    )

    class RCVHolder(item: View): RecyclerView.ViewHolder(item){
        var itemImage: ImageView = item.findViewById(R.id.rcViewItemImage)
        var itemName: TextView = item.findViewById(R.id.rcViewItemText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RCVHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_layout, parent, false)
        return RCVHolder(view)
    }

    override fun onBindViewHolder(holder: RCVHolder, position: Int) {
        holder.itemImage.setImageResource(figuresList.get(position).imageId)
        holder.itemName.text = figuresList.get(position).name
    }

    override fun getItemCount(): Int {
        return figuresList.size
    }
}