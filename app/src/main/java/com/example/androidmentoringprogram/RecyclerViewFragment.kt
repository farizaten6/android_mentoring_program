package com.example.androidmentoringprogram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewFragment : Fragment() {

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
        rvView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = RecyclerViewAdapter()
        }
        super.onViewCreated(view, savedInstanceState)
    }
}