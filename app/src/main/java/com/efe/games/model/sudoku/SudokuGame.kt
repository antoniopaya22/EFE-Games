package com.efe.games.model.sudoku

import android.os.SystemClock

/**
 * Juego de sudoko, tiene un tablero
 */
class SudokuGame {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var tiempo: Long = 0
    private var lastPlayed: Long = 0
    var creado: Long = 0
    var estado: Int = GAME_STATE_NOT_STARTED
    lateinit var tablero: Tablero
    private var acParaTiempo: Long = -1

    /**
     *  ====================================================
     *                      STATIC
     *  ====================================================
     */
    companion object {
        // CONSTANTS
        const val GAME_STATE_PLAYING = 0
        const val GAME_STATE_NOT_STARTED = 1
        const val GAME_STATE_COMPLETED = 2

        fun crearSudokuConTablero(tablero: Tablero): SudokuGame {
            val game = SudokuGame()
            game.tablero = tablero
            game.creado = System.currentTimeMillis()
            return game
        }
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun validar(): Boolean = this.tablero.validar()

    fun start() {
        estado = GAME_STATE_PLAYING
        resume()
    }

    private fun resume() {
        acParaTiempo = SystemClock.uptimeMillis()
    }

    private fun pause() {
        tiempo += SystemClock.uptimeMillis() - acParaTiempo
        acParaTiempo = -1
        lastPlayed = System.currentTimeMillis()
    }

    fun finish() {
        pause()
        estado = GAME_STATE_COMPLETED
    }


}