package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidmentoringprogram.R
import com.example.androidmentoringprogram.firstlesson.AlertDialogFragment
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_PATTERN = "yyyy-MM-dd"

class NewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var model: NewsViewModel
    private val newsTopics = listOf("software", "medicine", "travel", "culture",  "education")
    private var selectedTopic : String = newsTopics.first()

    companion object {
        var status = "ok"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        setupSpinner()

        model = ViewModelProvider(this).get(NewsViewModel::class.java)
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    model.container.stateFlow.collect {
                        render(it.articles)
                    }
                }
            }
        }
        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.container)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            val currentDate = SimpleDateFormat(DATE_PATTERN, Locale.US).format(Calendar.getInstance().time)
            model.getNews(selectedTopic, currentDate)
        }
    }

    private fun render(articles: List<Article>){
        val recyclerView = findViewById<RecyclerView>(R.id.newsRCView)
        val adapter: RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> = RecyclerViewNewsAdapter(articles, this)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter

        if (status != "ok") AlertDialogFragment(this).show("Error: $status")
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
        val currentDate = SimpleDateFormat(DATE_PATTERN, Locale.US).format(Calendar.getInstance().time)
        adapterView.selectedItem.toString().let {
            selectedTopic = it
            model.getNews(it, currentDate)
            findViewById<Toolbar>(R.id.toolbar).title = it.replaceFirstChar { it.uppercase() }
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

