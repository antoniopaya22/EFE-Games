package com.efe.games.controller.sudoku

import android.os.CountDownTimer
import com.efe.games.business.sudoku.GeneradorSudoku
import com.efe.games.business.sudoku.ResuelveSudoku
import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.business.sudoku.listeners.OnSudokuResueltoListener
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame
import com.efe.games.model.sudoku.Tablero
import com.efe.games.ui.sudoku.SudokuActivity
import com.efe.games.ui.sudoku.TableroSudokuView
import com.efe.games.ui.sudoku.TecladoView


object SudokuController {

    private lateinit var sudokuActivity: SudokuActivity
    private lateinit var tableroSudokuView: TableroSudokuView
    private lateinit var tecladoView: TecladoView
    private lateinit var timer: CountDownTimer
    private lateinit var onSudokuResueltoListener: OnSudokuResueltoListener
    private var tiempoParaResolver: Long = 0
    private const val padNumerico: Int = 0
    private var isRunning: Boolean = false

    fun iniciarPartida(
        sudokuActivity: SudokuActivity,
        tableroSudokuView: TableroSudokuView,
        dificultad: Int,
        tiempoParaResolver: Long
    ) {
        this.sudokuActivity = sudokuActivity
        this.tableroSudokuView = tableroSudokuView
        SudokuManager.nuevaPartida(generarSudoku(getHuecosPorDificultad(dificultad)), dificultad)
        this.tableroSudokuView.game = SudokuManager.game
        this.tiempoParaResolver = tiempoParaResolver
        this.startTime()
        this.isRunning = true
    }

    fun addTeclado(tecladoView: TecladoView) {
        this.tecladoView = tecladoView
        this.tecladoView.initialize(tableroSudokuView, SudokuManager.game)
    }

    fun resolverSudoku() {
        val tableroResuelto: Tablero = Tablero.crearTableroFromArray(
            ResuelveSudoku.resolverSudoku(SudokuManager.originalSudoku))
        SudokuManager.game = SudokuGame.crearSudokuConTablero(tableroResuelto)
        SudokuManager.game.estado = SudokuGame.GAME_STATE_COMPLETED
        tableroSudokuView.game = SudokuManager.game
        this.onPause()
    }

    fun setNotaCelda(celda: Celda, nota: NotaCelda) = SudokuManager.setNotaCelda(celda, nota)
    fun setValorCelda(celda: Celda, valor: Int) = SudokuManager.setValorCelda(celda, valor)

    fun activarTeclado() {
        tecladoView.activarMetodoEscritura(padNumerico)
    }

    private fun generarSudoku(numHuecos: Int): SudokuGame {
        SudokuManager.originalSudoku = GeneradorSudoku.generar(numHuecos)
        val tablero = Tablero.crearTableroFromArray(SudokuManager.originalSudoku)
        return SudokuGame.crearSudokuConTablero(tablero)
    }

    private fun getHuecosPorDificultad(dificultad: Int): Int =
        when (dificultad) {
            0 -> 20
            1 -> 5
            else -> 40
        }

    fun onSudokuResuelto() {
        TODO()
    }

    fun undo() {
        SudokuManager.undo()
    }


    // TIEMPO

    private fun pauseTime() = timer.cancel()
    private fun startTime() {
        timer = object : CountDownTimer (tiempoParaResolver, 1000){
            override fun onFinish() {
                updateTimeText(true)
            }

            override fun onTick(millisUntilFinished: Long) {
                tiempoParaResolver = millisUntilFinished
                updateTimeText(false)
            }
        }
        timer.start()
    }

    fun updateTimeText(acabo: Boolean) {
        if (acabo) sudokuActivity.setTitle("Vaya sacabao")
        else {
            val minute = (tiempoParaResolver / 1000) / 60
            val seconds = (tiempoParaResolver / 1000) % 60
            sudokuActivity.setTitle("$minute:$seconds")
        }
    }

    fun hayTiempo() {
        if(isRunning) {
            this.pauseTime()
            isRunning = false
        }else {
            this.startTime()
            isRunning = true
        }
    }

    fun onPause() {
        if(isRunning){
            this.pauseTime()
            isRunning = false
        }
    }

    fun onResume() {
        if(!isRunning) {
            startTime()
            isRunning = true
        }
    }
}