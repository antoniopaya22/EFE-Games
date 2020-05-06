package com.efe.games.ui

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import com.efe.games.R
import com.efe.games.controller.MusicController
import com.efe.games.controller.UserController

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val txtUsername = findViewById<TextView>(R.id.txtUsername)
        val txtPuntos = findViewById<TextView>(R.id.txtPuntos)
        val swMusica = findViewById<Switch>(R.id.swMusica)

        txtUsername.text = UserController.usuarioActual?.username
        txtPuntos.text = UserController.usuarioActual?.puntos.toString()

        val preferences: SharedPreferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)

        swMusica.isChecked = preferences.getBoolean("Musica", true)
        swMusica.setOnCheckedChangeListener { buttonView, isChecked ->
            val editor = preferences.edit()
            if(isChecked){
                editor.putBoolean("Musica", true)
                MusicController.startMusic(this)
                editor.commit()
                editor.apply()
            }else {
                editor.putBoolean("Musica", false)
                MusicController.stopMusic(this)
                editor.commit()
                editor.apply()
            }
        }
    }
}