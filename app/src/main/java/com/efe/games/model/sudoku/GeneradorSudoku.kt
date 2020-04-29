package com.efe.games.model.sudoku

import java.lang.Math.random
import java.util.*

class GeneradorSudoku {
    private val ANCHO_TABLERO = 9
    private val ALTO_TABLERO = 9

    var tablero: Array<Array<Int>> = Array (ALTO_TABLERO) { Array (ANCHO_TABLERO) {0} }

    fun generar(huecos: Int): Array<Array<Int>> {
        var realizado: Boolean = resolverCasillas(0,0)
        while (!realizado) realizado = resolverCasillas(0,0)
        hacerAgujeros(huecos)
        return tablero
    }

    private fun resolverCasillas(x: Int, y: Int): Boolean {
        val valores = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
        var nextX: Int
        var nextY = y
        var temp: Int
        var actual: Int
        val top = valores.size
        val r = Random()
        for (i in top - 1 downTo 1) {
            actual = r.nextInt(i)
            temp = valores[actual]
            valores[actual] = valores[i]
            valores[i] = temp
        }
        for (i in valores.indices) {
            if (insertarValorEnCasilla(x, y, valores[i])) {
                tablero[x][y] = valores[i]
                if (x == 8) {
                    if (y == 8) return true else {
                        nextX = 0
                        nextY = y + 1
                    }
                } else {
                    nextX = x + 1
                }
                if (resolverCasillas(nextX, nextY)) return true
            }
        }
        tablero[x][y] = 0
        return false
    }

    private fun insertarValorEnCasilla(x: Int, y:Int, valor: Int) : Boolean{
        for (i in 0..8) {
            if (valor == tablero[x][i]) return false
        }
        for (i in 0..8) {
            if (valor == tablero[i][y]) return false
        }
        var cornerX = 0
        var cornerY = 0
        if (x > 2) cornerX = if (x > 5) 6 else 3
        if (y > 2) cornerY = if (y > 5) 6 else 3
        var i = cornerX
        while (i < 10 && i < cornerX + 3) {
            var j = cornerY
            while (j < 10 && j < cornerY + 3) {
                if (valor == tablero[i][j]) return false
                j++
            }
            i++
        }
        return true
    }

    private fun hacerAgujeros(huecos: Int) {
        var casillasRestantes = ANCHO_TABLERO * ALTO_TABLERO
        var huecosRestantes = huecos
        for (i in 0..8) for (j in 0..8) {
            val temp: Double = huecosRestantes.toDouble() / casillasRestantes.toDouble()
            if (random() <= temp) {
                tablero[i][j] = 0
                huecosRestantes--
            }
            casillasRestantes--
        }
    }

}