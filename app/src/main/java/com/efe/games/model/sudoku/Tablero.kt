package com.efe.games.model.sudoku

class Tablero (
    var celdas: Array<Array<Celda>>,
    var sectores: Array<GrupoCeldas>,
    var filas: Array<GrupoCeldas>,
    var columnas: Array<GrupoCeldas>,
    val size: Int
){

}