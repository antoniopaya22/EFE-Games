package com.efe.games.business

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import com.efe.games.R


class MusicService : Service(){

    var mediaPlayer: MediaPlayer? = null
    var isPlaying: Boolean = false

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(isPlaying) return START_STICKY
        mediaPlayer = MediaPlayer.create(this, R.raw.music)
        mediaPlayer!!.isLooping = true
        mediaPlayer!!.start()
        isPlaying = true
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer!!.stop()
        isPlaying = false
        mediaPlayer!!.release()
        mediaPlayer = null
    }

}