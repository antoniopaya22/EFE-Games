package com.efe.games.model.sudoku.command

import com.efe.games.model.sudoku.Tablero


abstract class AbstractCeldaCommand : AbstractCommand() {

    fun setCells(t: Tablero) {
        tablero = t
    }

    protected lateinit var tablero: Tablero
        private set
}
