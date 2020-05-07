package com.efe.games.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.MusicController
import com.efe.games.controller.UserController
import com.efe.games.ui.sudoku.SudokuOptionsActivity
import com.efe.games.ui.tictactoe.TicTacToeActivity
import com.efe.games.ui.util.AnimacionButton
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    private var activity: MainActivity = this
    private var preferences: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SET PREFERENCES
        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
        UserController.init(preferences!!.getString("UserActivo", null))
        if (UserController.usuarioActual == null) {
            crearUsuario()
        }

        // Antonio
        val btn: Button = findViewById(R.id.buttonSudoku)
        btn.setOnClickListener {
            val myAnim: Animation = AnimationUtils.loadAnimation(activity, R.anim.bounce_animacion)
            val interpolator = AnimacionButton(0.8, 10.0)
            myAnim.interpolator = interpolator
            btn.startAnimation(myAnim)
            val intent = Intent(this, SudokuOptionsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
        }

        // Alba
        val btn2: Button = findViewById(R.id.buttonTicTacToe)
        btn2.setOnClickListener {
            val myAnim: Animation = AnimationUtils.loadAnimation(activity, R.anim.bounce_animacion)
            val interpolator = AnimacionButton(0.8, 10.0)
            myAnim.interpolator = interpolator
            btn2.startAnimation(myAnim)
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
        }

        // Boton opciones
        val btnOpciones : Button = findViewById(R.id.buttonOptions)
        btnOpciones.setOnClickListener {
            val myAnim: Animation = AnimationUtils.loadAnimation(activity, R.anim.bounce_animacion)
            val interpolator = AnimacionButton(0.8, 10.0)
            myAnim.interpolator = interpolator
            btnOpciones.startAnimation(myAnim)
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
        }

        // Boton info
        val btnInfo : Button = findViewById(R.id.buttonHelp)
        btnInfo.setOnClickListener {
            val myAnim: Animation = AnimationUtils.loadAnimation(activity, R.anim.bounce_animacion)
            val interpolator = AnimacionButton(0.8, 10.0)
            myAnim.interpolator = interpolator
            btnInfo.startAnimation(myAnim)
            infoAlert()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
        }

        // Boton Salir
        val btnSalir: Button = findViewById(R.id.buttonExit)
        btnSalir.setOnClickListener {
            val myAnim: Animation = AnimationUtils.loadAnimation(activity, R.anim.bounce_animacion)
            val interpolator = AnimacionButton(0.8, 10.0)
            myAnim.interpolator = interpolator
            btnSalir.startAnimation(myAnim)
            salirAlert()
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right)
        }
    }

    override fun onResume() {
        super.onResume()
        preferences = getSharedPreferences("EFE", Context.MODE_PRIVATE)
        if(preferences!!.getBoolean("Musica", true)) MusicController.startMusic(activity)
    }

    override fun onPause() {
        super.onPause()
        MusicController.stopMusic(activity)
    }

    override fun onBackPressed() {
        salirAlert()
    }

    private fun infoAlert() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setIcon(android.R.drawable.ic_dialog_info)
            .setMessage("App creada por Antonio Payá González y Alba Cotarelo Tuñón")
            .setTitle("Sobre los creadores")
        builder.apply {
            setNegativeButton("Son unos grandes", null)
        }
        builder.create()
        builder.show()
    }


    private fun salirAlert() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Saliendo de la app")
            .setTitle("¿Estás seguro de que quieres salir?")
        builder.apply {
            setPositiveButton("Si") { _, _ ->
                activity.finish()
                exitProcess(0)
            }
            setNegativeButton("No", null)
        }
        builder.create()
        builder.show()
    }

    private fun crearUsuario() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setView(input)
            .setTitle("Nombre de usuario")
            .setCancelable(false)
        builder.apply {
            setPositiveButton("Ok") { dialog, _ ->
                val username = input.text.toString()
                UserController.addUser(username)
                val editor = preferences!!.edit()
                editor.putString("UserActivo", username)
                editor.apply()
                dialog.dismiss()
            }
        }
        builder.create()
        builder.show()
    }
}
