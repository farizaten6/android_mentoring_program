package com.example.androidmentoringprogram

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewFragment : Fragment() {

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

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.recycler_view_fragment, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rvView: RecyclerView = view.findViewById(R.id.rcView)
        val rvView2: RecyclerView? = view.findViewById(R.id.rcView2)

        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecyclerViewAdapter(figuresList, indexShift = 0)
            }
        } else {
            rvView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecyclerViewAdapter(figuresList.subList(0, 5), indexShift = 0)
            }
            rvView2?.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecyclerViewAdapter(figuresList.subList(5, 10), indexShift = 5)
            }
        }

        super.onViewCreated(view, savedInstanceState)
    }
}