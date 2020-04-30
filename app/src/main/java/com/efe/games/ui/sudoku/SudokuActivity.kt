package com.efe.games.ui.sudoku

import android.app.Activity
import android.os.Bundle
import com.efe.games.R
import com.efe.games.controller.sudoku.SudokuController

class SudokuActivity : Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.Theme_Default)
        setContentView(R.layout.activity_sudoku)
        // Crear partida
        SudokuController.iniciarPartida(findViewById(R.id.tablero) ,1)
        // Añadir teclado
        SudokuController.addTeclado(findViewById(R.id.teclado))
        SudokuController.activarTeclado()
    }


}
