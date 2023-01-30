package com.example.androidmentoringprogram.secondlesson

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmentoringprogram.R
import com.example.androidmentoringprogram.databinding.ActivitySelectionBinding

class SongSelectionActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener{

    private lateinit var model: SongsListViewModel
    private lateinit var binding: ActivitySelectionBinding
    private val artists = mutableListOf("none")
    private val genres = mutableListOf("none")
    private var songs = emptyList<Song>()
    private var foundSongs = emptyList<Song>()
    private val names = mutableListOf<String>()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this).get(SongsListViewModel::class.java)
        model.songs.observe(this, Observer { it ->
            songs = it
            songs.forEach { artists.add(it.artist) }
            setupSpinner(R.id.artistsSpinner, artists)
            songs.forEach { genres.add(it.genre) }
            setupSpinner(R.id.genresSpinner, genres)
        } )
        model.foundSongs.observe(this, Observer { it ->
            foundSongs = it
            foundSongs.forEach { names.add("${it.artist}-${it.name}") }

            recyclerView = binding.songsRcview
            val adapter: RecyclerView.Adapter<RecyclerViewSongsAdapter.ViewHolder> = RecyclerViewSongsAdapter(names)
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        } )
        addSongsToDb()
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, position: Int, id: Long) {
        binding.apply {
            if (artists.get(position) == "none" && genres.get(position) == "none") {
                artistsSpinner.isEnabled = true
                genresSpinner.isEnabled = true
            }
            if (adapterView.id == artistsSpinner.id && adapterView.selectedItem.toString() != "none") {
                genresSpinner.isEnabled = false
                model.getFoundData(contentResolver, adapterView.selectedItem.toString(), null, null)
            }
            if (adapterView.id == genresSpinner.id && adapterView.selectedItem.toString() != "none") {
                artistsSpinner.isEnabled = false
                model.getFoundData(contentResolver, null, adapterView.selectedItem.toString(), null)
            }
            recyclerView = songsRcview
            val adapter: RecyclerView.Adapter<RecyclerViewSongsAdapter.ViewHolder> = RecyclerViewSongsAdapter(names)
            val layoutManager = LinearLayoutManager(applicationContext)
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
        }
    }

    private fun setupSpinner(id: Int, list: List<String>) {
        findViewById<Spinner>(id).setOnItemSelectedListener(this)
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, list.distinct())
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        findViewById<Spinner>(id).setAdapter(arrayAdapter)
    }

    private fun addSongsToDb(){
        DBHelper(this).apply {
            addSong(0L, R.raw.song, "MMM", "Instrumental", "Starry Night")
            addSong(1L, R.raw.skrillexwaytobreakmyheart, "Skrillex", "Hip hop", "Way to break my heart")
            addSong(2L, R.raw.samilacabellosouthoftheborder, "Camila Cabello", "Latin", "South of the border")
            addSong(3L, R.raw.khalidbeautifulpeople, "Khalid", "Pop", "Beautiful people")
            addSong(4L, R.raw.khalididontcare, "Khalid", "Pop", "I don't care")
            addSong(5L, R.raw.eminemrememberthename, "Eminem", "Rap", "Remember the name")
            addSong(6L, R.raw.ellamaiputitallonme, "Ella Mai", "Rock", "Put it all on me")
            addSong(7L, R.raw.heridontwantyourmoney, "H.E.R.", "Indie", "I don't want your money")
            addSong(8L, R.raw.travisscottantisocial, "Travis Scott", "Rap", "Antisocial")
            addSong(9L, R.raw.edsheerantakemebacktolondon, "Ed Sheeran", "R&B", "Take me back to London")
            addSong(10L, R.raw.edsheerandavenothingonyou, "Ed Sheeran", "Pop", "Dave nothing on you")
            addSong(11L, R.raw.edsheeranblow, "Ed Sheeran", "R&B", "BLOW")
        }
        model.getData(contentResolver)
    }

    override fun onNothingSelected(arg0: AdapterView<*>) {}
}