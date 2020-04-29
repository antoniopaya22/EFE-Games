package com.efe.games.model.sudoku.listeners

import com.efe.games.model.sudoku.Celda

interface OnTocarCeldaListener {
    fun onCellTapped(celda: Celda)
}