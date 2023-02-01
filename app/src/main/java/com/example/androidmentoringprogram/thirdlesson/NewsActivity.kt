package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R

class NewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var model: NewsViewModel
    private val newsTopics = listOf("software", "medicine", "travel", "culture",  "education")
    private var news = emptyList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupSpinner()

        model = ViewModelProvider(this).get(NewsViewModel::class.java)
        model.news.observe(this, Observer { it ->
            news = it
            val recyclerView = findViewById<RecyclerView>(R.id.newsRCView)
            val adapter: RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> = RecyclerViewNewsAdapter(news)
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        } )

    }

    private fun setupSpinner() {
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, newsTopics)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(R.id.newsSpinner).apply {
            onItemSelectedListener = this@NewsActivity
            adapter = arrayAdapter
        }
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        adapterView.selectedItem.toString().let {
            model.getNews(it)
            findViewById<Toolbar>(R.id.toolbar).title = it.replaceFirstChar { it.uppercase() }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

