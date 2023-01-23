package com.example.androidmentoringprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmentoringprogram.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PlayerService.startService(this, null)

        binding.apply {
            playButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Play")
            }
            pauseButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Pause")
            }
            stopButton.setOnClickListener {
                PlayerService.startService(this@PlayerActivity, "Stop")
            }
        }
    }
}