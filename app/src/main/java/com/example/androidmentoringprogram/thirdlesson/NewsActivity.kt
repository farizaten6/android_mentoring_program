package com.example.androidmentoringprogram.thirdlesson

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


class NewsActivity : AppCompatActivity() {
    lateinit var articles: List<Article>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        loadNews()
        val recyclerView = findViewById<RecyclerView>(R.id.newsRCView)
        val adapter: RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> = RecyclerViewNewsAdapter(articles)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    private fun loadNews() {
        runBlocking {
            launch {
                articles = try {
                    Api.instance.getNews().articles?.subList(0, 20) ?: emptyList()
                } catch (exception: Exception) {
                    Toast.makeText(this@NewsActivity, "$exception", Toast.LENGTH_LONG).show()
                    emptyList()
                }
            }
        }
    }
}

