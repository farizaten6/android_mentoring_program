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

const val NOTIFICATION_TITLE = "The music player is ON"
const val NOTIFICATION_TEXT = "tap to see details"
const val INTENT_ACTION_NAME = "inputExtra"
const val CHANNEL_ID = "ForegroundService"
const val CHANNEL_NAME = "Foreground Service Channel"

class PlayerService: Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener {

    private var mp: MediaPlayer? = null
    private var lastPosition = 0
    private var isPlaying = false

    companion object {
        fun startService(context: Context, message: String?) {
            val startIntent = Intent(context, PlayerService::class.java)
            message?.let { startIntent.putExtra(INTENT_ACTION_NAME, message) }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(context, startIntent)
            } else {
                context.startService(startIntent)
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(INTENT_ACTION_NAME)
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
        if (isPlaying) {
            mp?.start()
            return
        } else if (mp == null) {
            mp = MediaPlayer.create(this, R.raw.song)
            createNotificationChannel()
            startForegroundService()
        }
        mp?.seekTo(lastPosition)
        mp?.start()
        isPlaying = true
        lastPosition = 0
    }

    private fun handlePause() {
        mp?.pause()
    }

    private fun handleStop() {
        mp?.stop()
        mp?.prepareAsync()
        isPlaying = false
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun startForegroundService(){
        val pendingIntent: PendingIntent =
            Intent(this, PlayerActivity::class.java).let { notificationIntent ->
                PendingIntent.getActivity(applicationContext, 0, notificationIntent, FLAG_IMMUTABLE)
            }

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_TEXT)
            .setSmallIcon(R.drawable.ic_player)
            .setContentIntent(pendingIntent)
            .setOngoing(false)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForeground(1, notification)
        }
    }

    override fun onPrepared(p0: MediaPlayer?) {

    }

    override fun onError(p0: MediaPlayer?, p1: Int, p2: Int): Boolean {
        mp?.reset()
        return false
    }

    override fun onCompletion(p0: MediaPlayer?) {

    }

    private fun destroyPlayer() {
        lastPosition = mp!!.currentPosition
        mp?.stop()
        mp?.release()
        mp = null
        isPlaying = false
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyPlayer()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        destroyPlayer()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE)
        } else {
            stopForeground(true)
        }
        super.onTaskRemoved(rootIntent)
    }
}