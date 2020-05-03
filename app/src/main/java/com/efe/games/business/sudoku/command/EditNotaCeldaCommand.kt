package com.efe.games.business.sudoku.command

import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda

/**
 * Clase que implementa la interfaz EFECommand
 *
 * Edita la nota de una celda
 */
class EditNotaCeldaCommand() : EFECommand {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var row = 0
    private var col = 0
    private var nota: NotaCelda? = null
    private var notaOld: NotaCelda? = null
    override var isCheckpoint: Boolean = false

    /**
     *  ====================================================
     *                      CONSTRUCTOR
     *  ====================================================
     */
    constructor(celda: Celda, nota: NotaCelda?) : this() {
        row = celda.rowIndex
        col = celda.colIndex
        this.nota = nota
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    override fun execute() {
        val cell: Celda = SudokuManager.game.tablero.celdas[row][col]
        notaOld = cell.nota
        cell.nota = nota!!
    }

    override fun undo() {
        val cell: Celda = SudokuManager.game.tablero.celdas[row][col]
        cell.nota = notaOld!!
    }
}