package com.efe.games.business.sudoku.command

import com.efe.games.business.sudoku.SudokuManager
import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.Tablero
import java.util.*


class LimpiarNotasCommand : EFECommand {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */
    private val mOldNotes: MutableList<NoteEntry> = ArrayList()
    override var isCheckpoint: Boolean = false

    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */
    override fun execute() {
        mOldNotes.clear()
        for (r in 0 until Tablero.SUDOKU_SIZE) {
            for (c in 0 until Tablero.SUDOKU_SIZE) {
                val celda: Celda = SudokuManager.game!!.tablero.celdas[r][c]
                val nota = celda.nota
                if (!nota.isEmpty()) {
                    mOldNotes.add(NoteEntry(r, c, nota))
                    celda.nota = NotaCelda()
                }
            }
        }
    }

    override fun undo() {
        for (ne in mOldNotes) SudokuManager.game!!.tablero.celdas[ne.row][ne.col].nota = ne.nota
    }

    private class NoteEntry(var row: Int, var col: Int, var nota: NotaCelda)
}