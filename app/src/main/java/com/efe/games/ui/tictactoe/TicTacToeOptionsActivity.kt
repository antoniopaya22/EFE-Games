package com.efe.games.ui.tictactoe

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.efe.games.R
import com.efe.games.controller.MusicController
import com.efe.games.controller.util.InternetCheck


class TicTacToeOptionsActivity : AppCompatActivity() {

    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tictactoe_options)
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        radioGroup.check(R.id.rdAILimited)

        val btn: Button = findViewById(R.id.btPlayTicTacToe)
        btn.setOnClickListener {
            play()
        }

    }

    private fun play() {
        val radioGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val id = radioGroup.checkedRadioButtonId
        val checked = findViewById<RadioButton>(id).text
        var playmode = 0
        when(checked){
            getString(R.string.modeAILimited) -> playmode = 0
            getString(R.string.modeAIInfinite) -> playmode = 1
            getString(R.string.mode1v1Limited) -> playmode = 2
            getString(R.string.mode1v1Infinite) -> playmode = 3
        }
        if(playmode == 0 || playmode == 1) {
            checkInternetConnection(playmode)
        }else{
            startPlaying(playmode)
        }
    }

    private fun checkInternetConnection(playmode: Int){
        InternetCheck(object : InternetCheck.Consumer {
            override fun accept(internet: Boolean?) {
                if(internet!!) {
                    startPlaying(playmode)
                }
                else {
                    showNoInternetAlert()
                }
            }
        })
    }

    fun startPlaying(playmode:Int){
        val intent = Intent(this, TicTacToeActivity::class.java)
        intent.putExtra("playMode", playmode)
        startActivity(intent)
    }

    fun showNoInternetAlert(){
        println("ALERTTTTTTTTTTT")
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Conecta tu dispositivo a la red para jugar en este modo")
            .setTitle("No se pudo conectar")
        builder.apply {
            setPositiveButton("Aceptar") { _, _ ->  }
        }
        builder.create()
        builder.show()
    }

    override fun onResume() {
        super.onResume()
        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
        if(preferences!!.getBoolean("Musica", true)) MusicController.startMusic(this)
    }

    override fun onPause() {
        super.onPause()
        MusicController.stopMusic(this)
    }

}
