package com.example.androidmentoringprogram.secondlesson

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidmentoringprogram.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        var foundSongs: List<Song>
        var isPlaying = false
        val model = ViewModelProvider(this).get(SongsListViewModel::class.java)

        super.onCreate(savedInstanceState)
        val binding: ActivityPlayerBinding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            model.foundSongs.observe(this@PlayerActivity, Observer { it ->
                foundSongs = it
                foundSongs.first().apply {
                    songNameView.text = name
                    songArtistView.text = artist
                    songGenreView.text = genre
                    PlayerService.startService(this@PlayerActivity, songLink.toString())
                }
            })

            intent.getStringExtra(PlayerService.INTENT_ACTION_NAME)
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    model.getFoundData(contentResolver,null, null, it)
                }

            playButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Play")
                isPlaying = true
            }
            pauseButton.setOnClickListener {
                if (isPlaying){
                    PlayerService.startService(this@PlayerActivity, "Pause")
                }
            }
            stopButton.setOnClickListener {
                if (isPlaying) {
                    PlayerService.startService(this@PlayerActivity, "Stop")
                }
                isPlaying = false
            }
            artistSelectButton.setOnClickListener {
                val resultIntent = Intent(this@PlayerActivity, SongSelectionActivity::class.java)
                startActivity(resultIntent)
            }
        }
    }
}