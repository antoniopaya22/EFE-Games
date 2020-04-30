package com.efe.games.business.sudoku

import com.efe.games.model.sudoku.Tablero

object ResuelveSudoku {

    /**
     *  ====================================================
     *                      PROPIEDADES
     *  ====================================================
     */

    var tablero: Array<Array<Int>> = Array (Tablero.SUDOKU_SIZE) { Array (
        Tablero.SUDOKU_SIZE
    ) {0} }


    /**
     *  ====================================================
     *                      FUNCIONES
     *  ====================================================
     */

    fun resolverSudoku(tablero: Array<Array<Int>>): Array<Array<Int>> {
        this.tablero = tablero
        backtraking(tablero)
        return tablero
    }

    private fun backtraking(sudoku: Array<Array<Int>>): Boolean {
        var temp = false
        var i = 0
        var j = 0
        while (i < Tablero.SUDOKU_SIZE) {
            j = 0
            while (j < Tablero.SUDOKU_SIZE) {
                if (sudoku[i][j] == 0) {
                    temp = true
                    break
                }
                j++
            }
            if (temp) break
            i++
        }
        if (i == Tablero.SUDOKU_SIZE || j == Tablero.SUDOKU_SIZE) {
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
        for (i in 0 until Tablero.SUDOKU_SIZE) {
            if (sudoku[i][columna] == num) return false
        }
        return true
    }

    private fun comprobarFila(sudoku: Array<Array<Int>>, fila: Int, num: Int): Boolean {
        for (i in 0 until Tablero.SUDOKU_SIZE) {
            if (sudoku[fila][i] == num) return false
        }
        return true
    }
}