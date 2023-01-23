package com.example.androidmentoringprogram

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class PlayerService: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private val CHANNEL_ID = "ForegroundService"
    lateinit private var mp: MediaPlayer
    private var mAudioManager: AudioManager? = null

    companion object {
        fun startService(context: Context, message: String?) {
            val startIntent = Intent(context, PlayerService::class.java)
            message?.let { startIntent.putExtra("inputExtra", message) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(context, startIntent)
            } else {
                context.startService(startIntent)
            }
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, PlayerService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        createNotificationChannel()
        val notificationIntent = Intent(this, PlayerActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Player Service")
            .setContentText("content text")
            .setSmallIcon(R.drawable.ic_player)
            .setContentIntent(pendingIntent)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification)
        }

        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mp = MediaPlayer.create(this, R.raw.song)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra("inputExtra")
            ?.let {
                when (it) {
                    "Play" -> handlePlay()
                    "Pause" -> handlePause()
                    "Stop" -> handleStop()
                }
            }
        return START_NOT_STICKY
    }

    private fun handlePlay() {
        mp.start()
    }

    private fun handlePause() {
        mp.pause()
    }

    private fun handleStop() {
        mp.stop()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {

    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {

    }
}