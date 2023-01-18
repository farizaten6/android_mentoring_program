package com.example.androidmentoringprogram

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val rvView: RecyclerView = findViewById(R.id.rcView)
        rvView.apply {
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity)
            adapter = RecyclerViewAdapter()
        }
    }
}