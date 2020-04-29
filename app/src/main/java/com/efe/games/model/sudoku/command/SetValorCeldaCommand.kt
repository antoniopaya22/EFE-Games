package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Celda


class SetValorCeldaCommand() : AbstractCeldaCommand() {
    private var row = 0
    private var col = 0
    private var value = 0
    private var valueOld = 0

    constructor(celda: Celda, value: Int) : this() {
        row = celda.rowIndex
        col = celda.colIndex
        this.value = value
    }

    override fun execute() {
        val cell: Celda = tablero.celdas[row][col]
        valueOld = cell.value
        cell.value = value
    }

    override fun undo() {
        val cell: Celda = tablero.celdas[row][col]
        cell.value = valueOld
    }
}