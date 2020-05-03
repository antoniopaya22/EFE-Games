package com.efe.games.ui.sudoku

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.efe.games.R
import com.efe.games.controller.sudoku.SudokuController

class SudokuActivity : Activity() {

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
            R.id.resolverSudoku -> {
                SudokuController.resolverSudoku()
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
