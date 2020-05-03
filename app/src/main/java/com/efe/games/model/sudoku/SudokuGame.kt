package com.efe.games.model.sudoku


/**
 * Juego de sudoko, tiene un tablero
 */
class SudokuGame {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    var creado: Long = 0
    var estado: Int = GAME_STATE_NOT_STARTED
    lateinit var tablero: Tablero

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
    }

    fun finish() {
        estado = GAME_STATE_COMPLETED
    }


}