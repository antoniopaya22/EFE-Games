package com.efe.games.model.sudoku

import android.os.SystemClock
import com.efe.games.model.sudoku.command.*
import com.efe.games.model.sudoku.listeners.OnSudokuResueltoListener


class SudokuGame {

    var tiempo: Long = 0
    var lastPlayed: Long = 0
    var creado: Long = 0
    var estado: Int = GAME_STATE_NOT_STARTED
    var puntos: Int = 0
    lateinit var tablero: Tablero

    var onSudokuResueltoListener: OnSudokuResueltoListener? = null
    var commandStack: CommandStack? = null

    // Tiempo cuando la activitie se activa
    var acParaTiempo: Long = -1

    companion object {
        // CONSTANTS
        const val GAME_STATE_PLAYING = 0
        const val GAME_STATE_NOT_STARTED = 1
        const val GAME_STATE_COMPLETED = 2

        fun crearSudokuVacio(): SudokuGame{
            val game = SudokuGame()
            game.tablero = Tablero.crearTableroVacio()
            game.creado = System.currentTimeMillis()
            return game
        }

        fun crearSudokuConTablero(tablero: Tablero): SudokuGame {
            val game = SudokuGame()
            game.tablero = tablero
            game.creado = System.currentTimeMillis()
            return game
        }
    }

    fun setValorCelda(celda: Celda, valor: Int) {
        if (celda.editable) {
            executeCommand(SetValorCeldaCommand(celda, valor))
            puntos = if (tablero.validar()) puntos else puntos - 1
            if (tablero.isCompleted()) {
                finish()
                if (onSudokuResueltoListener != null) {
                    onSudokuResueltoListener!!.onSudokuResuelto()
                }
            }
        }
    }

    fun setNotaCelda(celda: Celda, nota: NotaCelda) {
        if (celda.editable) executeCommand(EditNotaCeldaCommand(celda, nota))
    }

    fun validar(): Boolean = this.tablero.validar()

    fun start() {
        estado = GAME_STATE_PLAYING
        resume()
    }

    fun resume() {
        acParaTiempo = SystemClock.uptimeMillis()
    }

    fun pause() {
        tiempo += SystemClock.uptimeMillis() - acParaTiempo
        acParaTiempo = -1
        lastPlayed = System.currentTimeMillis()
    }

    private fun finish() {
        pause()
        estado = GAME_STATE_COMPLETED
    }

    fun reset() {
        for (r in 0 until Tablero.SUDOKU_SIZE) for (c in 0 until Tablero.SUDOKU_SIZE) {
            val cell: Celda = tablero.celdas[r][c]
            if (cell.editable) {
                cell.value = 0
                cell.nota = NotaCelda()
            }
        }
        validar()
        tiempo = 0
        lastPlayed = 0
        estado = GAME_STATE_NOT_STARTED
    }


    fun undo() {
        commandStack!!.undo()
    }

    private fun executeCommand(c: AbstractCommand) = commandStack!!.execute(c)

    fun hasSomethingToUndo(): Boolean = commandStack!!.hasSomethingToUndo()

    fun setUndoCheckpoint() = commandStack!!.setCheckpoint()

    fun undoToCheckpoint() = commandStack!!.undoToCheckpoint()

    fun hasUndoCheckpoint(): Boolean = commandStack!!.hasCheckpoint()

    fun limpiarNotas() = executeCommand(LimpiarNotasCommand())

    fun fillNotas() = executeCommand(FillNotasCommand())

}