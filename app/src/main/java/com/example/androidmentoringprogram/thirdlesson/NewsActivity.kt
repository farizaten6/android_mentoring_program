package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class NewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var articles: List<Article>
    private val newsTopics = listOf("software", "medicine", "travel", "culture",  "education")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        setupSpinner()
        loadNews(newsTopics.first())
        val recyclerView = findViewById<RecyclerView>(R.id.newsRCView)
        val adapter: RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> = RecyclerViewNewsAdapter(articles)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun loadNews(topic: String) {
        runBlocking {
            launch {
                articles = try {
                    Api.instance.getNews(topic).articles?.subList(0, 20) ?: emptyList()
                } catch (exception: Exception) {
                    Toast.makeText(this@NewsActivity, "$exception", Toast.LENGTH_LONG).show()
                    emptyList()
                }
            }
        }
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
        loadNews(adapterView.selectedItem.toString())
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

