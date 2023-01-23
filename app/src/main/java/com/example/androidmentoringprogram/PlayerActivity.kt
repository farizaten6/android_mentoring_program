package com.example.androidmentoringprogram

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidmentoringprogram.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {
    lateinit var binding: ActivityPlayerBinding
    var isPlaying: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
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
        }
    }
}