package com.efe.games.model.sudoku

data class Celda (
    var row: Int,
    var col: Int,
    var value: Int,
    var editable: Boolean,
    var isValido: Boolean,
    var nota: NotaCelda
)