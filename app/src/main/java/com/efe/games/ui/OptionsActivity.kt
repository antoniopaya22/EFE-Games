package com.efe.games.ui

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import com.efe.games.R
import com.efe.games.controller.MusicController
import com.efe.games.controller.UserController
import com.google.android.material.textfield.TextInputEditText

class OptionsActivity : AppCompatActivity() {

    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_options)

        title = "Opciones"

        val txtUsername = findViewById<TextInputEditText>(R.id.txtEditUsername)
        val txtPuntos = findViewById<TextView>(R.id.txtPuntos)
        val swMusica = findViewById<Switch>(R.id.swMusica)

        txtUsername.setText(UserController.usuarioActual?.username, TextView.BufferType.EDITABLE)
        val puntosTest = "Tienes ${UserController.usuarioActual?.puntos.toString()} Puntos"
        txtPuntos.text = puntosTest

        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)

        swMusica.isChecked = preferences!!.getBoolean("Musica", true)
        swMusica.setOnCheckedChangeListener { _, isChecked ->
            val editor = preferences!!.edit()
            if(isChecked){
                editor.putBoolean("Musica", true)
                MusicController.startMusic(this)
                editor.apply()
            }else {
                editor.putBoolean("Musica", false)
                MusicController.stopMusic(this)
                editor.apply()
            }
        }

        val btnUsername = findViewById<Button>(R.id.usernameButton)
        btnUsername.setOnClickListener{
            val user = UserController.usuarioActual
            user!!.username = txtUsername.text.toString()
            UserController.update(user)
            UserController.usuarioActual = user
            preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
            val editor = preferences!!.edit()
            editor.putString("UserActivo", user.username)
            editor.apply()
            val builder: AlertDialog.Builder = this.let {
                AlertDialog.Builder(it)
            }
            builder
                .setIcon(android.R.drawable.checkbox_on_background)
                .setMessage("Has cambiado el nombre de usuario correctamente")
                .setTitle("Hola ${txtUsername.text} !!")
                .setCancelable(false)
            builder.apply {
                setNegativeButton("Ok", null)
            }
            builder.create()
            builder.show()
        }
    }
}