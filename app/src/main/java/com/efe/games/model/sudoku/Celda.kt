package com.efe.games.model.sudoku

/**
 * Celda del tablero
 */
data class Celda (
    var rowIndex: Int,
    var colIndex: Int,
    var value: Int,
    var editable: Boolean,
    var isValido: Boolean,
    var nota: NotaCelda,
    var row: GrupoCeldas,
    var col: GrupoCeldas,
    var sector: GrupoCeldas
)