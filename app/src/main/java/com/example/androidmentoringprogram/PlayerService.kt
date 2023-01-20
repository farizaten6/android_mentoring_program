package com.example.androidmentoringprogram

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.STREAM_MUSIC
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.os.Message
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class PlayerService: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private val CHANNEL_ID = "ForegroundService"
    private val mp = MediaPlayer.create(this, R.raw.starrynight)
//    private var mPlayOnPrepare = true
    private var mAudioManager: AudioManager? = null
    var mPlayer: MediaPlayer? = null

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, PlayerService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }
        fun stopService(context: Context) {
            val stopIntent = Intent(context, PlayerService::class.java)
            context.stopService(stopIntent)
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        mAudioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        initMediaPlayerIfNeeded()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
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
        startForeground(1, notification)

        val action = intent?.action
        when (action) {
            "Play" -> handlePlay()
            "Pause" -> handlePause()
            else -> return START_NOT_STICKY
        }

        return START_NOT_STICKY
    }

    private fun handlePlay() {
//        mPlayOnPrepare = true
        mp!!.start()
//        requestAudioFocus()
    }

    private fun handlePause() {
        mPlayer!!.pause()
    }

    private fun initMediaPlayerIfNeeded() {
        if (mPlayer != null) {
            return
        }

        mPlayer = MediaPlayer().apply {
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            setAudioStreamType(STREAM_MUSIC)
            setOnPreparedListener(this@PlayerService)
            setOnCompletionListener(this@PlayerService)
            setOnErrorListener(this@PlayerService)
        }
//        setupEqualizer()
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