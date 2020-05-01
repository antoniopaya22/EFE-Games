package com.efe.games.business.sudoku.listeners

import com.efe.games.model.sudoku.Celda

interface OnTocarCeldaListener {
    fun onCellTapped(celda: Celda)
}