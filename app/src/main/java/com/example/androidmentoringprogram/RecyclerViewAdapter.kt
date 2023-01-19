package com.example.androidmentoringprogram

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(
    private val itemsList: List<Figure>,
    private val indexShift: Int
): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(item: View): RecyclerView.ViewHolder(item){
        var itemImage: ImageView = item.findViewById(R.id.rcViewItemImage)
        var itemName: TextView = item.findViewById(R.id.rcViewItemText)
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
        holder.apply {
            itemImage.setImageResource(itemsList.get(position).imageId)
            itemName.text = itemsList.get(position).name
            itemNumber = position + 1 + indexShift
        }
    }

    override fun getItemCount(): Int {
        return itemsList.size
    }
}