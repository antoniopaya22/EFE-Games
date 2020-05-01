package com.efe.games.controller.sudoku

import com.efe.games.business.sudoku.GeneradorSudoku
import com.efe.games.business.sudoku.ResuelveSudoku
import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.business.sudoku.command.CommandStack
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView


object SudokuController {

    private var tableroSudokuView: TableroSudokuView? = null
    private var tecladoView: TecladoView? = null
    private const val padNumerico: Int = 0

    fun iniciarPartida(tableroSudokuView: TableroSudokuView, dificultad: Int) {
        SudokuManager.dificultad = dificultad
        this.tableroSudokuView = tableroSudokuView
        SudokuManager.game = generarSudoku(getHuecosPorDificultad(dificultad))
        SudokuManager.game!!.start()
        SudokuManager.onSudokuResueltoListener = SudokuManager.onSolvedListener
        tableroSudokuView.game = SudokuManager.game
    }

    fun addTeclado(tecladoView: TecladoView) {
        this.tecladoView = tecladoView
        this.tecladoView!!.initialize(tableroSudokuView, SudokuManager.game)
    }

    fun resolverSudoku() {
        val tableroResuelto: Tablero = Tablero.crearTableroFromArray(
            ResuelveSudoku.resolverSudoku(SudokuManager.originalSudoku!!))
        SudokuManager.game = SudokuGame.crearSudokuConTablero(tableroResuelto)
        SudokuManager.game!!.estado = SudokuGame.GAME_STATE_COMPLETED
        tableroSudokuView!!.game = SudokuManager.game
    }

    fun setNotaCelda(celda: Celda, nota: NotaCelda) = SudokuManager.setNotaCelda(celda, nota)
    fun setValorCelda(celda: Celda, valor: Int) = SudokuManager.setValorCelda(celda, valor)

    fun activarTeclado() {
        tecladoView!!.activarMetodoEscritura(padNumerico)
    }

    private fun generarSudoku(numHuecos: Int): SudokuGame {
        SudokuManager.originalSudoku = GeneradorSudoku.generar(numHuecos)
        val tablero = Tablero.crearTableroFromArray(SudokuManager.originalSudoku!!)
        return SudokuGame.crearSudokuConTablero(tablero)
    }

    private fun getHuecosPorDificultad(dificultad: Int): Int =
        when (dificultad) {
            0 -> 20
            1 -> 5
            else -> 40
        }
}