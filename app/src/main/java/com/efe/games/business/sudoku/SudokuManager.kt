package com.efe.games.business.sudoku

import com.efe.games.business.sudoku.command.*
import com.efe.games.business.sudoku.listeners.OnChangeListener
import com.efe.games.business.sudoku.listeners.OnSudokuResueltoListener
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.SudokuGame

/**
 * SINGLETON
 *
 * Sudoku Manager -> Se encarga la gestion de un juego de Sudoku
 */
object SudokuManager {
    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    var game: SudokuGame? = null
        set(value) {
            value!!.tablero.validar()
            commandStack = CommandStack()
            field = value
        }
    var dificultad: Int = 0
    var originalSudoku : Array<Array<Int>>? = null
    var onSudokuResueltoListener: OnSudokuResueltoListener? = null

    private var commandStack: CommandStack? = null // Command

    /**
     *  ====================================================
     *                  FUNCIONES - SETTERS
     *  ====================================================
     */
    fun setNotaCelda(celda: Celda, nota: NotaCelda) {
        if (celda.editable) executeCommand(EditNotaCeldaCommand(celda, nota))
        validarTablero()
    }

    fun setValorCelda(celda: Celda, valor: Int) {
        if (celda.editable) {
            executeCommand(SetValorCeldaCommand(celda, valor))
            if (game!!.tablero.isCompleted()) {
                game!!.finish()
                if (onSudokuResueltoListener != null) {
                    onSudokuResueltoListener!!.onSudokuResuelto()
                }
            }
            validarTablero()
        }
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */
    fun getValoresUsados() = game!!.tablero.getValoresUsados()

    fun validarTablero() = game!!.validar()

    fun limpiarNotas() = executeCommand(LimpiarNotasCommand())

    fun fillNotas() = executeCommand(FillNotasCommand())

    /**
     *  ====================================================
     *                      LISTENERS
     *  ====================================================
     */

    fun addTableroListener(listener: OnChangeListener) {
        game!!.tablero.addOnChangeListener(listener)
    }

    val onSolvedListener: OnSudokuResueltoListener = object : OnSudokuResueltoListener {
        override fun onSudokuResuelto() {
            println("Resuelto")
        }
    }

    /**
     *  ====================================================
     *                      COMMAND
     *  ====================================================
     */

    private fun executeCommand(c: EFECommand) = commandStack!!.execute(c)

    fun undo() {
        commandStack!!.undo()
    }

    fun hasSomethingToUndo(): Boolean = commandStack!!.hasSomethingToUndo()

    fun setUndoCheckpoint() = commandStack!!.setCheckpoint()

    fun undoToCheckpoint() = commandStack!!.undoToCheckpoint()

    fun hasUndoCheckpoint(): Boolean = commandStack!!.hasCheckpoint()
}