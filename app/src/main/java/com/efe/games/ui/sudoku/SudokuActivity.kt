package com.efe.games.ui.sudoku

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.efe.games.R
import com.efe.games.model.sudoku.GeneradorSudoku
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero

class SudokuActivity : Activity() {

    lateinit var tableroView: TableroSudokuView
    lateinit var generador: GeneradorSudoku
    lateinit var sudoku: Array<Array<Int>>
    lateinit var tableroOriginal: Tablero
    lateinit var game: SudokuGame
    var numHuecos: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTheme(R.style.Theme_Default)
        setContentView(R.layout.activity_sudoku)
        tableroView = findViewById(R.id.tablero)

        // Generar partida
        numHuecos = 25
        generador = GeneradorSudoku()
        sudoku = generador.generar(numHuecos)
        val tablero = Tablero.crearTableroFromArray(sudoku)
        tableroOriginal = tablero
        game = SudokuGame.crearSudokuConTablero(tablero)

        // Comenzar partida
        game.start()
        tableroView.setGame(game)
    }

    override fun onResume() {
        super.onResume()

    }
}
