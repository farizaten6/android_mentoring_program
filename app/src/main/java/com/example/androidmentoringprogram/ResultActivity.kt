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
    private val artists = mutableListOf("none")
    private val genres = mutableListOf("none")
    private var songs = emptyList<Song>()
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
        var songs: List<Song>
        val names: MutableList<String> = mutableListOf()

        if (artists.get(position) == "none" && genres.get(position) == "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = true
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = true
        }

        if (adapterView.id == findViewById<Spinner>(R.id.artistsSpinner).id && adapterView.selectedItem.toString() != "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = true
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = false
            songs = (db as DBHelper).findSong(adapterView.selectedItem.toString(), null, null)
            songs.forEach {
                names.add("${it.artist}-${it.name}")
            }
        }
        if (adapterView.id == findViewById<Spinner>(R.id.genresSpinner).id && adapterView.selectedItem.toString() != "none") {
            findViewById<Spinner>(R.id.artistsSpinner).isEnabled = false
            findViewById<Spinner>(R.id.genresSpinner).isEnabled = true
            songs = (db as DBHelper).findSong(null, adapterView.selectedItem.toString(), null)
            songs.forEach {
                names.add("${it.artist}-${it.name}")
            }
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
        db = DBHelper(this)
        (db as DBHelper).addSong(0L, R.raw.song, "MMM", "Instrumental", "Starry Night")
        (db as DBHelper).addSong(1L, R.raw.skrillexwaytobreakmyheart, "Skrillex", "Hip hop", "Way to break my heart")
        (db as DBHelper).addSong(2L, R.raw.samilacabellosouthoftheborder, "Camila_Cabello", "Latin", "South of the border")
        (db as DBHelper).addSong(3L, R.raw.khalidbeautifulpeople, "Khalid", "Pop", "Beautiful people")
        (db as DBHelper).addSong(4L, R.raw.khalididontcare, "Khalid", "Pop", "I don't care")
        (db as DBHelper).addSong(5L, R.raw.eminemrememberthename, "Eminem", "Rap", "Remember the name")
        (db as DBHelper).addSong(6L, R.raw.ellamaiputitallonme, "Ella_Mai", "Rock", "Put it all on me")
        (db as DBHelper).addSong(7L, R.raw.heridontwantyourmoney, "H.E.R.", "Indie", "I don't want your money")
        (db as DBHelper).addSong(8L, R.raw.travisscottantisocial, "Travis Scott", "Rap", "Antisocial")
        (db as DBHelper).addSong(9L, R.raw.edsheerantakemebacktolondon, "Ed Sheeran", "R&B", "Take me back to London")
        (db as DBHelper).addSong(10L, R.raw.edsheerandavenothingonyou, "Ed Sheeran", "Pop", "Dave nothing on you")
        (db as DBHelper).addSong(11L, R.raw.edsheeranblow, "Ed Sheeran", "R&B", "BLOW")
        model.getData(contentResolver)
    }

}