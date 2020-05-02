package com.efe.games.business.sudoku.listeners

interface OnNotaEditadaListener {
    fun onNoteEdit(numeros: Array<Int>): Boolean
}