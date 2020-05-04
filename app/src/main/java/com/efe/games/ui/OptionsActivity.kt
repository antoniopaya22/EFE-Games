package com.efe.games.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.efe.games.R
import com.efe.games.controller.UserController

class OptionsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        val txtUsername = findViewById<TextView>(R.id.txtUsername)
        val txtPuntos = findViewById<TextView>(R.id.txtPuntos)

        txtUsername.text = UserController.usuarioActual?.username
        txtPuntos.text = UserController.usuarioActual?.puntos.toString()
    }
}