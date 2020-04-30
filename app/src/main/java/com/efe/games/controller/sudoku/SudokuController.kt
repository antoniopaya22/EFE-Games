package com.efe.games.controller.sudoku

import com.efe.games.business.sudoku.GeneradorSudoku
import com.efe.games.business.sudoku.ResuelveSudoku
import com.efe.games.business.sudoku.escritura.PadNumerico
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView

object SudokuController {

    private var originalSudoku : Array<Array<Int>>? = null
    private var game: SudokuGame? = null
    private var tableroInicial: Tablero? = null
    private var tableroSudokuView: TableroSudokuView? = null
    private var tecladoView: TecladoView? = null
    private const val padNumerico: Int = 0

    fun iniciarPartida(tableroSudokuView: TableroSudokuView, dificultad: Int) {
        this.tableroSudokuView = tableroSudokuView
        game = generarSudoku(getHuecosPorDificultad(dificultad))
        tableroInicial = game!!.tablero
        tableroSudokuView.game = game
    }

    fun resolverSudoku() {
        val tableroResuelto: Tablero = Tablero.crearTableroFromArray(
            ResuelveSudoku.resolverSudoku(originalSudoku!!))
        game = SudokuGame.crearSudokuConTablero(tableroResuelto)
        game!!.estado = SudokuGame.GAME_STATE_COMPLETED
        tableroSudokuView!!.game = game
    }

    fun addTeclado(tecladoView: TecladoView) {
        this.tecladoView = tecladoView
        this.tecladoView!!.initialize(tableroSudokuView, game)
    }

    fun activarTeclado() {
        tecladoView!!.activarMetodoEscritura(padNumerico)
    }

    fun setNotaCelda(celda: Celda, nota: NotaCelda) {
        if (celda.editable) if (game != null)
            game!!.setNotaCelda(celda, nota) else celda.nota = nota
    }

    fun setValorCelda(celda: Celda, valor: Int) {
        if (celda.editable) if (game != null)
            game!!.setValorCelda(celda, valor) else celda.value = valor
    }

    private fun getHuecosPorDificultad(dificultad: Int): Int =
        when (dificultad) {
            0 -> 20
            1 -> 30
            else -> 40
        }

    private fun generarSudoku(numHuecos: Int): SudokuGame {
        originalSudoku = GeneradorSudoku.generar(numHuecos)
        val tablero = Tablero.crearTableroFromArray(originalSudoku!!)
        return SudokuGame.crearSudokuConTablero(tablero)
    }

}