package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Celda
import com.efe.games.model.sudoku.NotaCelda
import com.efe.games.model.sudoku.Tablero
import java.util.*


class LimpiarNotasCommand : AbstractCeldaCommand() {
    private val mOldNotes: MutableList<NoteEntry> =
        ArrayList()

    override fun execute() {
        val tablero: Tablero = tablero
        mOldNotes.clear()
        for (r in 0 until Tablero.SUDOKU_SIZE) {
            for (c in 0 until Tablero.SUDOKU_SIZE) {
                val celda: Celda = tablero.celdas[r][c]
                val nota = celda.nota
                if (!nota.isEmpty()) {
                    mOldNotes.add(NoteEntry(r, c, nota))
                    celda.nota = NotaCelda()
                }
            }
        }
    }

    override fun undo() {
        val tablero: Tablero = tablero
        for (ne in mOldNotes) {
            tablero.celdas[ne.row][ne.col].nota = ne.nota
        }
    }

    private class NoteEntry(var row: Int, var col: Int, var nota: NotaCelda)
}