package com.efe.games.business.sudoku.command

import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.GrupoCeldas
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.Tablero
import java.util.*


class FillNotasCommand : EFECommand {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private var notasViejas: List<NotaEntry> = ArrayList<NotaEntry>()
    override var isCheckpoint: Boolean = false

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */
    override fun execute() {
        notasViejas = ArrayList<NotaEntry>()
        for (r in 0 until Tablero.SUDOKU_SIZE) {
            for (c in 0 until Tablero.SUDOKU_SIZE) {
                val celda: Celda = SudokuManager.game.tablero.celdas[r][c]
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
        for (ne in notasViejas) SudokuManager.game.tablero.celdas[ne.row][ne.col].nota = ne.nota
    }

    private class NotaEntry(var row: Int, var col: Int, var nota: NotaCelda)
}