package com.efe.games.ui.sudoku

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.efe.games.R
import com.efe.games.controller.sudoku.SudokuController

class SudokuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.Theme_Default)
        setContentView(R.layout.activity_sudoku)
        val tiempoParaResolver = 1*60000L
        // Crear partida
        SudokuController.iniciarPartida(this, findViewById(R.id.tablero), 1, tiempoParaResolver)
        // Añadir teclado
        SudokuController.addTeclado(findViewById(R.id.teclado))
        SudokuController.activarTeclado()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.sudoku_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.undo -> {
                SudokuController.undo()
                return true
            }
            R.id.resolverSudoku -> {
                val builder: AlertDialog.Builder = this.let {
                    AlertDialog.Builder(it)
                }
                builder.setMessage("Si resuelves el sudoku no obtendrás puntos")
                    .setTitle("¿Quieres resolverlo?")
                builder.apply {
                    setPositiveButton("Ok") { dialog, id ->
                        SudokuController.resolverSudoku()
                    }
                    setNegativeButton("Cancelar") { _, _ ->
                        // User cancelled the dialog
                    }
                }
                builder.create()
                builder.show()
                true
            }
            R.id.hayTiempo -> {
                SudokuController.hayTiempo()
                item.isChecked = !item.isChecked
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPause() {
        super.onPause()
        SudokuController.onPause()
    }

    override fun onResume() {
        super.onResume()
        SudokuController.onResume()
    }

}
