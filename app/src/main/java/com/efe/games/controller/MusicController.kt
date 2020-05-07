package com.efe.games.controller

import android.content.Context
import android.content.Intent
import com.efe.games.business.MusicService

object MusicController {

    var isPlaying: Boolean = false;

    fun startMusic(context: Context) {
        context.startService(Intent(context, MusicService::class.java))
        isPlaying = true
    }

    fun stopMusic(context: Context) {
        context.stopService(Intent(context, MusicService::class.java))
        isPlaying = false
    }

}