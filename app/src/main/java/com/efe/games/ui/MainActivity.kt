package com.efe.games.ui

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.UserController
import com.efe.games.model.User
import com.efe.games.ui.sudoku.SudokuActivity
import com.efe.games.ui.sudoku.SudokuOptionsActivity
import com.efe.games.ui.tictactoe.TicTacToeActivity


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val preferences: SharedPreferences = getSharedPreferences("Users", Context.MODE_PRIVATE)

        UserController.init(preferences.getString("UserActivo", null))
        if (UserController.usuarioActual == null) {
            crearUsuario()
        }

        // Antonio
        val btn: Button = findViewById(R.id.buttonSudoku)
        btn.setOnClickListener {
            val intent = Intent(this, SudokuOptionsActivity::class.java)
            startActivity(intent)
        }

        // Alba
        val btn2: Button = findViewById(R.id.buttonTicTacToe)
        btn2.setOnClickListener {
            val intent = Intent(this, TicTacToeActivity::class.java)
            startActivity(intent)
        }

        // Boton opciones
        val btnOpciones : Button = findViewById(R.id.buttonOptions)
        btnOpciones.setOnClickListener {
            val intent = Intent(this, OptionsActivity::class.java)
            startActivity(intent)
        }

        // Boton Salir
        val btnSalir: Button = findViewById(R.id.buttonExit)
        btnSalir.setOnClickListener {
            salirAlert()
        }
    }

    override fun onBackPressed() {
        salirAlert()
    }

    fun salirAlert() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        builder
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage("Saliendo de la app")
            .setTitle("¿Estás seguro de que quieres salir?")
        builder.apply {
            setPositiveButton("Si") { _, _ ->
                finish()
            }
            setNegativeButton("No", null)
        }
        builder.create()
        builder.show()
    }

    fun crearUsuario() {
        val builder: AlertDialog.Builder = this.let {
            AlertDialog.Builder(it)
        }
        var input = EditText(this)
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
                val preferences: SharedPreferences = getSharedPreferences("Users", Context.MODE_PRIVATE)
                val editor = preferences.edit()
                editor.putString("UserActivo", username)
                editor.apply()
                dialog.dismiss()
            }
        }
        builder.create()
        builder.show()
    }
}
