package com.efe.games.business.sudoku.listeners

import com.efe.games.model.sudoku.Celda

interface OnCeldaSeleccionadaListener {
    fun onCellSelect(celda: Celda)
}