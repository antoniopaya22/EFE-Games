package com.efe.games.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.ui.sudoku.SudokuActivity
import com.efe.games.ui.sudoku.SudokuOptionsActivity
import com.efe.games.ui.tictactoe.TicTacToeActivity
import com.efe.games.ui.tictactoe.TicTacToeOptionsActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Antonio
        val btn: Button = findViewById(R.id.buttonSudoku)
        btn.setOnClickListener {
            val intent = Intent(this, SudokuOptionsActivity::class.java)
            startActivity(intent)
        }

        // Alba
        val btn2: Button = findViewById(R.id.buttonTicTacToe)
        btn2.setOnClickListener {
            val intent = Intent(this, TicTacToeOptionsActivity::class.java)
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
            .setTitle("¿Estás seguro de que quieres salir")
        builder.apply {
            setPositiveButton("Si") { _, _ ->
                finish()
            }
            setNegativeButton("No", null)
        }
        builder.create()
        builder.show()
    }
}
