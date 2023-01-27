package com.example.androidmentoringprogram

import android.content.Intent
import android.database.sqlite.SQLiteOpenHelper
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmentoringprogram.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        lateinit var db: SQLiteOpenHelper
        lateinit var song: List<Song>
        var isPlaying = false

        super.onCreate(savedInstanceState)
        val binding: ActivityPlayerBinding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = DBHelper(this)

        binding.apply {
            intent.getStringExtra(PlayerService.INTENT_ACTION_NAME)
                ?.takeIf { it.isNotEmpty() }
                ?.let {
                    song = (db as DBHelper).findSong(null, null, it)
                    songNameView.text = song.first().name
                    songArtistView.text = song.first().artist
                    songGenreView.text = song.first().genre
                    PlayerService.startService(this@PlayerActivity, song.first().songLink.toString())
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
                val resultIntent = Intent(this@PlayerActivity, ResultActivity::class.java)
                startActivity(resultIntent)
            }
        }
    }
}