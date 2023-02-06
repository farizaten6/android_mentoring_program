package com.example.androidmentoringprogram.thirdlesson

import android.app.ProgressDialog
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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.androidmentoringprogram.R
import com.example.androidmentoringprogram.firstlesson.AlertDialogFragment
import java.text.SimpleDateFormat
import java.util.*

private const val DATE_PATTERN = "yyyy-MM-dd"
private const val LOADING_MESSAGE = "Loading..."

class NewsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private lateinit var model: NewsViewModel
    private lateinit var mProgressDialog: ProgressDialog
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
        mProgressDialog = ProgressDialog(this)

        model = ViewModelProvider(this).get(NewsViewModel::class.java)
        model.news.observe(this, Observer { it ->
            val recyclerView = findViewById<RecyclerView>(R.id.newsRCView)
            val adapter: RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> = RecyclerViewNewsAdapter(it, this)
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            mProgressDialog.cancel()
            if (status != "ok") AlertDialogFragment(this).show("Error: $status")
        } )

        val swipeRefreshLayout: SwipeRefreshLayout = findViewById(R.id.container)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            val currentDate = SimpleDateFormat(DATE_PATTERN, Locale.US).format(Calendar.getInstance().time)
            showProgressDialog()
            model.getNews(selectedTopic, currentDate, applicationContext)
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
        val currentDate = SimpleDateFormat(DATE_PATTERN, Locale.US).format(Calendar.getInstance().time)
        showProgressDialog()
        adapterView.selectedItem.toString().let {
            selectedTopic = it
            model.getNews(it, currentDate, applicationContext)
            findViewById<Toolbar>(R.id.toolbar).title = it.replaceFirstChar { it.uppercase() }
        }
    }

    private fun showProgressDialog(){
        mProgressDialog.setMessage(LOADING_MESSAGE)
        mProgressDialog.show()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

