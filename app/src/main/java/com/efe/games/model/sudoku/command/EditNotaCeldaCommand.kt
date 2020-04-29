package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda


class EditNotaCeldaCommand () : AbstractCeldaCommand() {
    private var row = 0
    private var col = 0
    private var nota: NotaCelda? = null
    private var notaOld: NotaCelda? = null

    constructor(celda: Celda, nota: NotaCelda?) : this() {
        row = celda.rowIndex
        col = celda.colIndex
        this.nota = nota
    }

    override fun execute() {
        val cell: Celda = tablero.celdas[row][col]
        notaOld = cell.nota
        cell.nota = nota!!
    }

    override fun undo() {
        val cell: Celda = tablero.celdas[row][col]
        cell.nota = notaOld!!
    }
}