package com.example.androidmentoringprogram.firstlesson

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R

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
        if (activity?.resources?.configuration?.orientation == Configuration.ORIENTATION_PORTRAIT) {
            rvView.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = RecyclerViewAdapter()
            }
        } else {
            rvView.apply {
                layoutManager = GridLayoutManager(activity, 3)
                adapter = RecyclerViewAdapter()
            }
        }


        super.onViewCreated(view, savedInstanceState)
    }
}