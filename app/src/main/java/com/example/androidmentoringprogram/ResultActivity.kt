package com.example.androidmentoringprogram

import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResultActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    lateinit var model: SongsListViewModel
    private lateinit var db: SQLiteOpenHelper
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerViewSongsAdapter.ViewHolder>? = null
    val artists = mutableListOf("none")
    val genres = mutableListOf("none")
    var songs = emptyList<Song>()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        model = ViewModelProvider(this).get(SongsListViewModel::class.java)
        model.songs.observe(this, Observer { it ->
            songs = it
            songs.forEach { artists.add(it.artist) }
            setupSpinner(R.id.artistsSpinner, artists)
            songs.forEach { genres.add(it.genre) }
            setupSpinner(R.id.genresSpinner, genres)
        } )

        findViewById<Button>(R.id.songAdditionButton).setOnClickListener {
            addSongsToDb()
        }

    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {

        recyclerView = findViewById(R.id.songsRcview)
        var names: List<String> = emptyList()

        if (artists.get(position) == "none" && genres.get(position) == "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = true
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = true
        }

        if (adapterView.id == findViewById<Spinner>(R.id.artistsSpinner).id && adapterView.selectedItem.toString() != "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = true
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = false
            names = (db as DBHelper).findSong(adapterView.selectedItem.toString(), null)
        }

        if (adapterView.id == findViewById<Spinner>(R.id.genresSpinner).id && adapterView.selectedItem.toString() != "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = false
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = true
            names = (db as DBHelper).findSong(null, adapterView.selectedItem.toString())
        }

        adapter = RecyclerViewSongsAdapter(names)
        val layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = adapter
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {

    }

    private fun setupSpinner(id: Int, list: List<String>) {
        findViewById<Spinner>(id).setOnItemSelectedListener(this)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list.distinct())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(id).setAdapter(arrayAdapter)
    }

    private fun addSongsToDb(){
        val id = (1..1000000).random().toLong()
        db = DBHelper(this)
        (db as DBHelper).addSong(id, "R.raw.song", "MMM", "Instrumental pop", "Name 1")
        (db as DBHelper).addSong(id.plus(1), "R.raw.song", "GGG", "G", "Name 2")
        (db as DBHelper).addSong(id.plus(2), "R.raw.song", "MMM", "G", "Name 3")
        (db as DBHelper).addSong(id.plus(3), "R.raw.song", "GGG", "Instrumental pop", "Name 4")
        model.getData(contentResolver)
    }

}