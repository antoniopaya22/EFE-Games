package com.efe.games.business.sudoku.command

import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.model.sudoku.Celda


class SetValorCeldaCommand (celda: Celda, private var value: Int) : EFECommand {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var row = 0
    private var col = 0
    private var valueOld = 0
    override var isCheckpoint: Boolean = false

    init {
        row = celda.rowIndex
        col = celda.colIndex
    }

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */
    override fun execute() {
        val cell: Celda = SudokuManager.game!!.tablero.celdas[row][col]
        valueOld = cell.value
        cell.value = value
    }

    override fun undo() {
        val cell: Celda = SudokuManager.game!!.tablero.celdas[row][col]
        cell.value = valueOld
    }
}