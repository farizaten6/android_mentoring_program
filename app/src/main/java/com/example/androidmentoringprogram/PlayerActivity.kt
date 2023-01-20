package com.example.androidmentoringprogram

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmentoringprogram.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_player)

        val binding = ActivityPlayerBinding.inflate(layoutInflater)

        PlayerService.startService(this, "")

        binding.apply {
            playButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Play")
            }
            pauseButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Pause")
            }
            stopButton.setOnClickListener {
                PlayerService.stopService(this@PlayerActivity)
            }
        }
    }
}