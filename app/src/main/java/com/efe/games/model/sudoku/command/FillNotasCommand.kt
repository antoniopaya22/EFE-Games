package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.GrupoCeldas
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.Tablero
import java.util.*


class FillNotasCommand : AbstractCeldaCommand() {
    private var notasViejas: List<NotaEntry> = ArrayList<NotaEntry>()

    override fun execute() {
        val tablero: Tablero = tablero
        notasViejas = ArrayList<NotaEntry>()
        for (r in 0 until Tablero.SUDOKU_SIZE) {
            for (c in 0 until Tablero.SUDOKU_SIZE) {
                val celda: Celda = tablero.celdas[r][c]
                notasViejas.plus(NotaEntry(r, c, celda.nota))
                celda.nota = NotaCelda()
                val row = celda.row
                val column: GrupoCeldas = celda.col
                val sector = celda.sector
                for (i in 1..Tablero.SUDOKU_SIZE) {
                    if (!row.contains(i) && !column.contains(i) && !sector.contains(i)) {
                        celda.nota = celda.nota.addNumber(i)
                    }
                }
            }
        }
    }

    override fun undo() {
        val tab: Tablero = this.tablero
        for (ne in notasViejas) tab.celdas[ne.row][ne.col].nota = ne.nota
    }

    private class NotaEntry(var row: Int, var col: Int, var nota: NotaCelda)
}