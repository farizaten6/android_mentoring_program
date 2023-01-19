package com.example.androidmentoringprogram

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val orientation: Int?
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val figuresList = listOf(
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

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var itemImage: ImageView = item.findViewById(R.id.rcViewItemImage)
        var itemName: TextView = item.findViewById(R.id.rcViewItemText)
        var itemImage2: ImageView? = item.findViewById(R.id.rcViewItemImage2)
        var itemName2: TextView? = item.findViewById(R.id.rcViewItemText2)
        var itemNumber: Int? = null

        init {
            item.setOnClickListener {
                val itemNumberWords = when (itemNumber) {
                    1 -> " (первый)"
                    2 -> " (второй)"
                    3 -> " (третий)"
                    4 -> " (четвертый)"
                    5 -> " (пятый)"
                    6 -> " (шестой)"
                    7 -> " (седьмой)"
                    8 -> " (восьмой)"
                    9 -> " (девятый)"
                    10 -> " (десятый)"
                    else -> null
                }
                AlertDialogFragment(item.context).show(itemNumber.toString() + itemNumberWords)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            holder.itemImage.setImageResource(figuresList.get(position).imageId)
            holder.itemName.text = figuresList.get(position).name
            holder.itemNumber = position + 1
        } else {
            if (position % 2 == 0) {
                holder.itemImage.setImageResource(figuresList.get(position).imageId)
                holder.itemName.text = figuresList.get(position).name
                holder.itemNumber = position + 1
            } else {
                holder.itemImage2?.setImageResource(figuresList.get(position).imageId)
                holder.itemName2?.text = figuresList.get(position).name
                holder.itemNumber = position + 1
            }
        }
    }

    override fun getItemCount(): Int {
        return figuresList.size
    }
}