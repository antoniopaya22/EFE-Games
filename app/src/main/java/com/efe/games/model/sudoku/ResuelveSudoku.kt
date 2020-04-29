package com.efe.games.model.sudoku

class ResuelveSudoku(
    var tablero: Array<Array<Int>>
) {
    private val ANCHO_TABLERO: Int = 9
    private val ALTO_TABLERO: Int = 9

    fun resolverSudoku(): Array<Array<Int>> {
        backtraking(tablero)
        return tablero
    }

    private fun backtraking(sudoku: Array<Array<Int>>): Boolean {
        var temp = false
        var i = 0
        var j = 0
        while (i < ANCHO_TABLERO) {
            j = 0
            while (j < ALTO_TABLERO) {
                if (sudoku[i][j] == 0) {
                    temp = true
                    break
                }
                j++
            }
            if (temp) break
            i++
        }
        if (i == ANCHO_TABLERO || j == ALTO_TABLERO) {
            return true
        }
        for (num in 1..9) {
            if (comprobarNumero(sudoku, i, j, num)) {
                sudoku[i][j] = num
                if (backtraking(sudoku)) return true
            }
            sudoku[i][j] = 0
        }
        return false
    }

    private fun comprobarNumero(sudoku: Array<Array<Int>>, fila: Int, columna: Int, num: Int): Boolean {
        return (comprobarColumna(sudoku, columna, num) && comprobarFila(sudoku, fila, num)
                && comprobarCuadrado(sudoku, fila, columna, num))
    }

    private fun comprobarCuadrado(sudoku: Array<Array<Int>>, fila: Int, columna: Int, num: Int): Boolean {
        val newRow = fila - fila % 3
        val newCol = columna - columna % 3
        for (i in 0..2) {
            for (j in 0..2) {
                if (sudoku[i + newRow][j + newCol] == num) return false
            }
        }
        return true
    }

    private fun comprobarColumna(sudoku: Array<Array<Int>>, columna: Int, num: Int): Boolean {
        for (i in 0 until ANCHO_TABLERO) {
            if (sudoku[i][columna] == num) return false
        }
        return true
    }

    private fun comprobarFila(sudoku: Array<Array<Int>>, fila: Int, num: Int): Boolean {
        for (i in 0 until ALTO_TABLERO) {
            if (sudoku[fila][i] == num) return false
        }
        return true
    }
}